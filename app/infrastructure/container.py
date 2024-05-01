from dependency_injector import containers, providers

from app.domain.entities.informacion_empresa import InformacionEmpresaFactory
from app.infrastructure.jms.kafka_consumer_service import KafkaConsumerService
from app.infrastructure.jms.kafka_producer_service import KafkaProducerService
from app.application.services.investigar_empresa_service import InvestigarEmpresaService
from app.infrastructure.handlers import Handlers
from app.infrastructure.repositories.informacion_empresa_rag import InformacionEmpresaMongoRepository


class Container(containers.DeclarativeContainer):
    # loads all handlers where @injects are set
    wiring_config = containers.WiringConfiguration(modules=Handlers.modules())

    # Factories
    informacion_empresa_factory = providers.Factory(InformacionEmpresaFactory)

    # Repositories
    informacion_empresa_rag_repository = providers.Singleton(InformacionEmpresaMongoRepository)

    # Servicio que depende de las anteriores
    investigar_empresa_service = providers.Factory(
        InvestigarEmpresaService,
        informacion_empresa_rag_repository=informacion_empresa_rag_repository
    )
    
    procesar_informacion_empresa_message = providers.Factory(
        investigar_empresa_service=investigar_empresa_service
    )

    kafka_consumer_service = providers.Singleton(
        KafkaConsumerService,
        topic='empresaPublisherTopic',
        # Pasa las dependencias necesarias, si las hay.
    )

    kafka_producer_service = providers.Singleton(
        KafkaProducerService,
        bootstrap_servers='humble-hornet-11005-us1-kafka.upstash.io:9092',
        topic='empresaListenerTopic',
    )
