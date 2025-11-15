# language: es
Característica: Captura y Consulta de Datos de Telemetría
  Como Sistema de Monitoreo IoT
  Quiero capturar y almacenar datos de telemetría de los dispositivos
  Para proporcionar información en tiempo real sobre las condiciones de la carga

  Antecedentes:
    Dado que el sistema de telemetría está operativo
    Y existe una sesión de monitoreo activa con ID "SESSION-100"

  Escenario: Capturar datos de telemetría con todos los parámetros
    Dado que el dispositivo IoT está transmitiendo datos
    Cuando recibo datos de telemetría con los siguientes valores:
      | temperatura | 25.5  |
      | humedad     | 60.0  |
      | vibracion   | 0.2   |
      | latitud     | -12.0464 |
      | longitud    | -77.0428 |
    Entonces los datos se almacenan correctamente en la sesión "SESSION-100"
    Y el sistema registra la marca de tiempo de recepción
    Y los datos quedan disponibles para consulta inmediata

  Escenario: Capturar datos de temperatura extremadamente baja
    Dado que el dispositivo detecta condiciones de frío extremo
    Cuando recibo datos de telemetría con temperatura de -40.0 grados
    Entonces el sistema almacena el valor de temperatura -40.0
    Y se debe generar una alerta de temperatura fuera de rango
    Y los datos quedan registrados en el historial

  Escenario: Capturar datos de temperatura extremadamente alta
    Dado que el dispositivo detecta condiciones de calor extremo
    Cuando recibo datos de telemetría con temperatura de 60.0 grados
    Entonces el sistema almacena el valor de temperatura 60.0
    Y se debe generar una alerta de temperatura fuera de rango
    Y los datos quedan registrados en el historial

  Escenario: Capturar datos con vibración elevada
    Dado que el vehículo está en una carretera con baches
    Cuando recibo datos de telemetría con vibración de 5.0 unidades
    Entonces el sistema almacena el valor de vibración 5.0
    Y se debe generar una alerta de vibración excesiva
    Y se registra la ubicación GPS donde ocurrió el evento

  Escenario: Capturar múltiples lecturas de telemetría en secuencia
    Dado que el dispositivo está transmitiendo continuamente
    Cuando recibo 10 lecturas de telemetría en intervalos de 30 segundos
    Entonces el sistema almacena las 10 lecturas correctamente
    Y cada lectura tiene una marca de tiempo única
    Y las lecturas están ordenadas cronológicamente
    Y todas las lecturas están asociadas a la sesión "SESSION-100"

  Escenario: Consultar datos de telemetría por sesión
    Dado que la sesión "SESSION-200" tiene 5 lecturas de telemetría almacenadas
    Cuando consulto los datos de telemetría de la sesión "SESSION-200"
    Entonces el sistema devuelve las 5 lecturas
    Y las lecturas incluyen temperatura, humedad, vibración y coordenadas GPS
    Y los datos están ordenados por fecha de captura

  Escenario: Rastrear cambios de ubicación GPS durante el viaje
    Dado que el vehículo está en movimiento
    Cuando recibo datos de telemetría de diferentes ubicaciones:
      | ubicacion  | latitud    | longitud   |
      | Miraflores | -12.1192   | -77.0350   |
      | Callao     | -12.0566   | -77.1181   |
      | San Isidro | -12.0975   | -77.0369   |
    Entonces el sistema registra todas las ubicaciones correctamente
    Y puedo trazar la ruta del vehículo
    Y cada punto tiene su temperatura y humedad asociada

  Escenario: Rechazar datos de telemetría en sesión inactiva
    Dado que la sesión "SESSION-300" tiene estado "PAUSED"
    Cuando intento enviar datos de telemetría a la sesión "SESSION-300"
    Entonces el sistema rechaza los datos
    Y muestra un error "Cannot add telemetry data to inactive session"
    Y los datos no son almacenados

  Escenario: Validar integridad de datos de telemetría
    Dado que recibo datos de telemetría
    Cuando los valores de humedad están entre 0% y 100%
    Y las coordenadas GPS son válidas para Perú
    Entonces el sistema acepta y almacena los datos
    Y marca los datos como válidos

  Escenario: Consultar historial completo de telemetría para auditoría
    Dado que una sesión "SESSION-400" está completada
    Y la sesión tiene 1000 lecturas de telemetría
    Cuando consulto el historial completo de la sesión
    Entonces el sistema devuelve todas las 1000 lecturas
    Y incluye temperatura mínima, máxima y promedio
    Y incluye todos los eventos de alerta registrados
    Y los datos están disponibles para exportación
