from app.infrastructure.schemas.hoja_de_vida_dto import FormularioDto


class InvestigarEmpresaService:

    async def execute(self, formulario: FormularioDto) -> list[str]:

        return ["¿Qué diferencias se encuentran entre interfaces y clases?",
                "¿Qué problemas se pueden encontrar dentro de la multi herencia?",
                "¿Por qué se necesitan métodos por defecto y pueden éstos anular un método Object?",
                "¿Cómo se pueden encontrar duplicados en una base de datos relacional utilizando SQL?"]
