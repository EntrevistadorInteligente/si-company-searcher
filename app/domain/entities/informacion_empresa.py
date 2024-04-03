from app.domain.exceptions import PriceIsLessThanOrEqualToZero, StockIsLessThanOrEqualToZero


class InformacionEmpresa:

    def __init__(self, empresa: str, perfil: str, seniority: str, pais: str,
                 informacion_empresa_vect: list[str]):
        self.__validate_price(empresa)
        self.__validate_stock(informacion_empresa_vect)

        self.empresa = empresa
        self.perfil = perfil
        self.seniority = seniority
        self.pais = pais
        self.informacion_empresa_vect = informacion_empresa_vect

    @staticmethod
    def __validate_price(id_entrevista: str):
        if not id_entrevista:
            raise PriceIsLessThanOrEqualToZero

    @staticmethod
    def __validate_stock(informacion_empresa_vect: list[str]):
        if informacion_empresa_vect.__sizeof__() <= 0:
            raise StockIsLessThanOrEqualToZero


class InformacionEmpresaFactory:

    @staticmethod
    def create(empresa: str, perfil: str, seniority: str, pais: str,
               informacion_empresa_vect: list[str]) -> InformacionEmpresa:
        return InformacionEmpresa(empresa, perfil, seniority, pais, informacion_empresa_vect)
