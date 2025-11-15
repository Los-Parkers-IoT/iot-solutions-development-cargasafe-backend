# language: es
Característica: Gestión de Sesiones de Monitoreo en Tiempo Real
  Como Gestor de Transporte
  Quiero iniciar y gestionar sesiones de monitoreo para mis viajes
  Para poder rastrear las condiciones de la carga en tiempo real

  Antecedentes:
    Dado que el sistema de monitoreo está disponible
    Y tengo un dispositivo IoT con ID "DEVICE-123"
    Y tengo un viaje programado con ID "TRIP-456"

  Escenario: Iniciar una nueva sesión de monitoreo exitosamente
    Dado que tengo credenciales válidas de gestor de transporte
    Cuando inicio una sesión de monitoreo para el dispositivo "DEVICE-123" y viaje "TRIP-456"
    Entonces el sistema crea una nueva sesión con estado "ACTIVE"
    Y la sesión registra la hora de inicio
    Y la sesión está vinculada al dispositivo "DEVICE-123"
    Y la sesión está vinculada al viaje "TRIP-456"

  Escenario: Pausar una sesión de monitoreo activa
    Dado que existe una sesión de monitoreo activa con ID "SESSION-001"
    Cuando pauso la sesión de monitoreo "SESSION-001"
    Entonces el estado de la sesión cambia a "PAUSED"
    Y el sistema deja de aceptar nuevos datos de telemetría para esa sesión

  Escenario: Reanudar una sesión de monitoreo pausada
    Dado que existe una sesión de monitoreo pausada con ID "SESSION-002"
    Cuando reanudo la sesión de monitoreo "SESSION-002"
    Entonces el estado de la sesión cambia a "ACTIVE"
    Y el sistema vuelve a aceptar datos de telemetría

  Escenario: Completar una sesión de monitoreo al finalizar el viaje
    Dado que existe una sesión de monitoreo activa con ID "SESSION-003"
    Y el viaje asociado ha llegado a su destino
    Cuando completo la sesión de monitoreo "SESSION-003"
    Entonces el estado de la sesión cambia a "COMPLETED"
    Y la sesión registra la hora de finalización
    Y el sistema ya no acepta datos de telemetría para esa sesión

  Escenario: Intentar pausar una sesión que no está activa
    Dado que existe una sesión de monitoreo con estado "PAUSED"
    Cuando intento pausar nuevamente la sesión
    Entonces el sistema muestra un error "Can only pause an active session"
    Y el estado de la sesión no cambia

  Escenario: Intentar completar una sesión ya completada
    Dado que existe una sesión de monitoreo con estado "COMPLETED"
    Cuando intento completar nuevamente la sesión
    Entonces el sistema muestra un error "Session is already completed"
    Y no se modifica la hora de finalización original

  Escenario: Consultar sesiones activas por dispositivo
    Dado que existen varias sesiones de monitoreo en el sistema
    Y una sesión activa está asociada al dispositivo "DEVICE-999"
    Cuando consulto las sesiones del dispositivo "DEVICE-999"
    Entonces el sistema devuelve la sesión activa
    Y la sesión contiene toda la información de telemetría asociada

  Escenario: Ciclo completo de vida de una sesión de monitoreo
    Dado que inicio una nueva sesión de monitoreo
    Cuando la sesión pasa por los estados: ACTIVE -> PAUSED -> ACTIVE -> COMPLETED
    Entonces cada transición de estado es registrada correctamente
    Y la sesión mantiene la integridad de todos sus datos
    Y la hora de finalización es posterior a la hora de inicio
