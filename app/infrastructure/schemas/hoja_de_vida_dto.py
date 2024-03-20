from typing import List, Optional
from pydantic import BaseModel
from enum import Enum


class FormularioDto(BaseModel):
    empresa: Optional[str] = None
    perfil: Optional[str] = None
    seniority: Optional[str] = None
    pais: Optional[str] = None


class PerfilEntrevistaDto(BaseModel):
    id_entrevista: Optional[str] = None
    evento_entrevista_id: Optional[str] = None
    formulario: FormularioDto


class EstadoProcesoEnum(str, Enum):
    AC = "AC"
    CVA = "CVA"
    FN = "FN"


class ProcesoEntrevistaDto(BaseModel):
    uuid: Optional[str] = None
    estado: EstadoProcesoEnum
    fuente: Optional[str] = None
    error: Optional[str] = None


class MensajeAnalizadorDto(BaseModel):
    proceso_entrevista: ProcesoEntrevistaDto
    id_entrevista: Optional[str] = None



