from dependency_injector import containers, providers
from app.application.services.kafka_consumer_service import KafkaConsumerService
from app.application.services.kafka_producer_service import KafkaProducerService
from app.application.services.investigar_empresa_service import InvestigarEmpresaService
from app.infrastructure.handlers import Handlers


class Container(containers.DeclarativeContainer):
    # loads all handlers where @injects are set
    wiring_config = containers.WiringConfiguration(modules=Handlers.modules())

    # Servicio que depende de las anteriores
    investigar_empresa_service = providers.Factory(
        InvestigarEmpresaService
    )

    process_cv_message = providers.Factory(
        investigar_empresa_service=investigar_empresa_service
    )

    kafka_consumer_service = providers.Singleton(
        KafkaConsumerService,
        topic='kafkaTopic',
        # Pasa las dependencias necesarias, si las hay.
    )

    kafka_producer_service = providers.Singleton(
        KafkaProducerService,
        bootstrap_servers='localhost:9092',
        topic='resumeTopic',
        # Pasa las dependencias necesarias, si las hay.
    )
