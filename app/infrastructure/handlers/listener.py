from fastapi import Depends, APIRouter

from app.infrastructure.jms.kafka_producer_service import KafkaProducerService
from app.application.services.investigar_empresa_service import InvestigarEmpresaService
from app.infrastructure.container import Container
from dependency_injector.wiring import Provide, inject
import json
from app.infrastructure.schemas.informacion_empresa_dto import ProcesoEntrevistaDto, EstadoProcesoEnum, PerfilEntrevistaDto

router = APIRouter(
    prefix='/analizador-empresa',
    tags=['analizador']
)


@router.get('/', response_model=str)
@inject
async def procesar_empresa(message,
                           investigar_empresa_service:
                           InvestigarEmpresaService = Depends(Provide[Container.investigar_empresa_service]),
                           kafka_producer_service:
                           KafkaProducerService = Depends(Provide[Container.kafka_producer_service])
                           ):
    data = json.loads(message.value.decode('utf-8'))

    id_entrevista = data.get('id_entrevista')

    perfil_entrevista_dto = PerfilEntrevistaDto(
        id_entrevista=id_entrevista,
        evento_entrevista_id=data.get('evento_entrevista_id'),
        formulario=data.get('formulario')
    )

    preguntas = await investigar_empresa_service.ejecutar(perfil_entrevista_dto.formulario)

    proceso_entrevista = ProcesoEntrevistaDto(
        uuid=perfil_entrevista_dto.evento_entrevista_id,
        estado=EstadoProcesoEnum.FN,
        fuente="ANALIZADOR"
    )

    await kafka_producer_service.send_message({
        "proceso_entrevista": proceso_entrevista.dict(),
        "id_entrevista": id_entrevista,
        "formulario": perfil_entrevista_dto.formulario.dict(),
        "preguntas": preguntas})
