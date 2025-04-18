# Message Aggregator Service

Este servicio agrega mensajes de WhatsApp por remitente en ventanas de tiempo (por defecto 30 segundos) antes de enviarlos para ser procesados.

## Arquitectura

El proyecto sigue una arquitectura hexagonal:

- **Dominio**: Modelos y reglas de negocio
- **Puertos**: Interfaces que definen cómo el dominio interactúa con el exterior
- **Adaptadores**: Implementaciones concretas de los puertos (MongoDB, Kafka, etc.)

## Flujo de datos completo

```
┌─────────────────┐        ┌───────────────────┐        ┌───────────────────┐        ┌────────────────┐
│  Webhook Service│        │WhatsappKafkaListener        │MongoWhatsappMessageRepo    │ Aggregator     │
│  (otro micro)   │        │                   │        │                   │        │ Service        │
└────────┬────────┘        └─────────┬─────────┘        └─────────┬─────────┘        └────────┬───────┘
         │                           │                            │                           │
         │ Publica mensaje           │                            │                           │
         │ en whatsapp.incoming      │                            │                           │
         │─────────────────────────> │                            │                           │
         │                           │ Consume mensaje            │                           │
         │                           │ guarda en MongoDB          │                           │
         │                           │─────────────────────────>  │                           │
         │                           │                            │ Notifica nuevos          │
         │                           │                            │ mensajes vía Change      │
         │                           │                            │ Stream                   │
         │                           │                            │ ─────────────────────────>
         │                           │                            │                           │ Agrupa por senderId
         │                           │                            │                           │ durante 30 segundos
         │                           │                            │                           │
         │                           │                            │                           │ Publica batch en
         │                           │                            │                           │ whatsapp.batched
         │                           │                            │                           │
```

## ¿Por qué guardamos en MongoDB?

1. **Persistencia**: Los mensajes quedan guardados incluso si el servicio se cae.
2. **Recuperación**: Al reiniciar, el agregador puede recuperar mensajes no procesados.
3. **Trazabilidad**: Podemos hacer consultas, monitoreo y debugging sobre los mensajes.
4. **Desacoplamiento**: Separamos la recepción/consumo de la agregación.
5. **Historial**: Podemos mantener un historial de conversaciones.

## Proceso de agregación

1. El Webhook Service publica cada mensaje individual en el topic `whatsapp.incoming`.
2. `WhatsappKafkaListener` consume de `whatsapp.incoming` y guarda en MongoDB.
3. `MongoWhatsappMessageRepository` usa Change Streams para detectar nuevos mensajes.
4. Por cada mensaje nuevo, el `WhatsappAggregatorService` lo redirige al sink del remitente.
5. El sink de cada remitente agrupa mensajes por 30 segundos usando `bufferTimeout()`.
6. Al cerrarse la ventana, se crea un `WhatsappBatch` con todos los mensajes del remitente.
7. El `WhatsappBatchPublisherAdapter` publica el lote en el topic `whatsapp.batched`.
8. Otro servicio (ej: Chatbot) consume de `whatsapp.batched` y procesa el lote completo.

## Ventajas de este diseño

- **Alta disponibilidad**: Si el servicio se cae, los mensajes quedan en MongoDB.
- **Escalabilidad**: Puedes tener múltiples instancias consumiendo y agregando.
- **Aislamiento**: Cada componente tiene una única responsabilidad.
- **Mantenibilidad**: Fácil de entender, testear y modificar cada componente.
- **Observabilidad**: Logs detallados y métricas en cada paso. 