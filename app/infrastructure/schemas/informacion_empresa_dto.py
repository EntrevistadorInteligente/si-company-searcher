from typing import List, Optional
from pydantic import BaseModel
from enum import Enum


class InformacionEmpresaDto(BaseModel):
    id_informacion_empresa_rag: Optional[str] = None
    empresa: Optional[str] = None
    perfil: Optional[str] = None
    seniority: Optional[str] = None
    pais: Optional[str] = None
    descripcion_vacante: Optional[str] = None
    preguntas: List[str] = []


class PerfilEntrevistaDto(BaseModel):
    id_entrevista: Optional[str] = None
    evento_entrevista_id: Optional[str] = None
    formulario: InformacionEmpresaDto


class EstadoProcesoEnum(str, Enum):
    AC = "AC"
    CVA = "CVA"
    FN = "FN"


class ProcesoEntrevistaDto(BaseModel):
    uuid: Optional[str] = None
    estado: EstadoProcesoEnum
    fuente: Optional[str] = None
    error: Optional[str] = None


class MensajeAnalizadorEmpresaDto(BaseModel):
    proceso_entrevista: ProcesoEntrevistaDto
    id_entrevista: Optional[str] = None
    formulario: InformacionEmpresaDto


