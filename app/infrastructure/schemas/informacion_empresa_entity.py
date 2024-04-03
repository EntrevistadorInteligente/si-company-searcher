from typing import Optional
from pydantic import BaseModel


class InformacionEmpresaEntityRag(BaseModel):
    empresa: Optional[str] = None
    perfil: Optional[str] = None
    seniority: Optional[str] = None
    pais: Optional[str] = None
    informacion_empresa_vect: Optional[list[str]] = None

