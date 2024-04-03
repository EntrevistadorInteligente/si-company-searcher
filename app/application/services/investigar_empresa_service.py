from app.domain.entities.informacion_empresa import InformacionEmpresaFactory
from app.domain.repositories.informacion_empresa_rag import InformacionEmpresaRepository
from app.infrastructure.schemas.informacion_empresa_dto import FormularioDto


class InvestigarEmpresaService:
    def __init__(self, informacion_empresa_rag_repository: InformacionEmpresaRepository):
        self.informacion_empresa_rag_repository = informacion_empresa_rag_repository

    async def ejecutar(self, formulario: FormularioDto) -> list[str]:

        preguntas = ["¿Qué diferencias se encuentran entre interfaces y clases?",
                     "¿Qué problemas se pueden encontrar dentro de la multi herencia?",
                     "¿Por qué se necesitan métodos por defecto y pueden éstos anular un método Object?",
                     "¿Cómo se pueden encontrar duplicados en una base de datos relacional utilizando SQL?"]

        await self.informacion_empresa_rag_repository.add(InformacionEmpresaFactory
                                                          .create(formulario.empresa, formulario.perfil,
                                                                  formulario.seniority, formulario.pais,
                                                                  preguntas))

        return preguntas

