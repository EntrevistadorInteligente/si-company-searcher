from fastapi import Depends, APIRouter

from app.application.services.kafka_producer_service import KafkaProducerService
from app.application.services.investigar_empresa_service import InvestigarEmpresaService
from app.infrastructure.container import Container
from dependency_injector.wiring import Provide, inject
import json
from app.infrastructure.schemas.hoja_de_vida_dto import ProcesoEntrevistaDto, EstadoProcesoEnum

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

    hoja_de_vida_dto = await investigar_empresa_service.execute()

    await kafka_producer_service.send_message(hoja_de_vida_dto)



