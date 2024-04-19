from app.domain.entities.informacion_empresa import InformacionEmpresaFactory
from app.domain.repositories.informacion_empresa_rag import InformacionEmpresaRepository
from app.infrastructure.schemas.informacion_empresa_dto import InformacionEmpresaDto
from langchain.text_splitter import CharacterTextSplitter


class InvestigarEmpresaService:
    def __init__(self, informacion_empresa_rag_repository: InformacionEmpresaRepository):
        self.informacion_empresa_rag_repository = informacion_empresa_rag_repository

    async def ejecutar(self, formulario: InformacionEmpresaDto) -> str:

        preguntas = ("¿Qué diferencias se encuentran entre interfaces y clases?"
                     f"¿Qué problemas se pueden encontrar dentro de la multi herencia?"
                     f"¿Por qué se necesitan métodos por defecto y pueden éstos anular un método Object?"
                     f"¿Cómo se pueden encontrar duplicados en una base de datos relacional utilizando SQL?")

        # Dividir el texto en chunks
        text_splitter = CharacterTextSplitter(
            separator="\n",
            chunk_size=800,
            chunk_overlap=50,
            length_function=len
        )

        text_chunks = text_splitter.split_text(preguntas)

        informacion_empresa = InformacionEmpresaFactory.create(formulario.empresa, formulario.perfil,
                                                               formulario.seniority, formulario.pais,
                                                               formulario.descripcion_vacante, text_chunks)

        return await self.informacion_empresa_rag_repository.add(informacion_empresa)

