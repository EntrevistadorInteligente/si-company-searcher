from abc import ABC, abstractmethod

from app.domain.entities.informacion_empresa import InformacionEmpresa


class InformacionEmpresaRepository(ABC):

    @abstractmethod
    async def add(self, informacion_empresa: InformacionEmpresa) -> str:
        raise NotImplemented

