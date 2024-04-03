from motor.motor_asyncio import AsyncIOMotorClient

from app.domain.entities.informacion_empresa import InformacionEmpresa, InformacionEmpresaFactory
from app.domain.repositories.informacion_empresa_rag import InformacionEmpresaRepository
from app.infrastructure.schemas.informacion_empresa_entity import InformacionEmpresaEntityRag

# MongoDB connection URL
MONGO_URL = "mongodb://root:secret@localhost:27017/"
client = AsyncIOMotorClient(MONGO_URL)
database = client["recopilador_informacion_empresa_rag"]
collection = database["informacion_empresa"]


class InformacionEmpresaMongoRepository(InformacionEmpresaRepository):

    async def add(self, informacion_empresa: InformacionEmpresa) -> str:
        proceso_entrevista = InformacionEmpresaEntityRag(
            empresa=informacion_empresa.empresa,
            perfil=informacion_empresa.perfil,
            seniority=informacion_empresa.seniority,
            pais=informacion_empresa.pais,
            informacion_empresa_vect=informacion_empresa.informacion_empresa_vect
        )
        result = await collection.insert_one(proceso_entrevista.dict())
        return result.inserted_id
