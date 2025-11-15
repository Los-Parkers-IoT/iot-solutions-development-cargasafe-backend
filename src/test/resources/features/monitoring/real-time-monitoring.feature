# language: es
Característica: Monitoreo en Tiempo Real de Condiciones de Carga
  Como Gestor de Transporte
  Quiero monitorear en tiempo real las condiciones de mi carga
  Para tomar acciones inmediatas ante cualquier desviación

  Antecedentes:
    Dado que soy un gestor de transporte autenticado
    Y tengo un viaje activo con monitoreo habilitado

  Escenario: Visualizar estado actual de temperatura y humedad
    Dado que el viaje está en tránsito
    Y el dispositivo está transmitiendo datos
    Cuando accedo al dashboard de monitoreo en tiempo real
    Entonces puedo ver la temperatura actual de la carga
    Y puedo ver la humedad actual de la carga
    Y los valores se actualizan cada 30 segundos

  Escenario: Detectar temperatura fuera del rango permitido
    Dado que el rango de temperatura permitido es de 2°C a 8°C
    Cuando la temperatura alcanza 12°C
    Entonces el sistema genera una alerta de "TEMPERATURE" de alta prioridad
    Y envío una notificación push al gestor de transporte
    Y el evento queda registrado en el historial con marca de tiempo
    Y se muestra la ubicación GPS donde ocurrió el evento

  Escenario: Detectar humedad fuera del rango permitido
    Dado que el rango de humedad permitido es de 40% a 70%
    Cuando la humedad alcanza 80%
    Entonces el sistema genera una alerta de "HUMIDITY" de alta prioridad
    Y envío una notificación al gestor
    Y se registra el evento con los valores específicos

  Escenario: Monitorear múltiples vehículos simultáneamente
    Dado que tengo 5 vehículos en tránsito
    Y cada vehículo tiene su sesión de monitoreo activa
    Cuando accedo al dashboard de flota
    Entonces puedo ver el estado de los 5 vehículos
    Y cada vehículo muestra temperatura, humedad y ubicación actual
    Y puedo identificar rápidamente vehículos con alertas

  Escenario: Recibir alerta de vibración excesiva
    Dado que el umbral de vibración es 2.0 unidades
    Cuando el dispositivo reporta vibración de 4.5 unidades
    Entonces el sistema genera una alerta de "VIBRATION"
    Y registro la ubicación exacta del evento
    Y puedo correlacionar con el mapa para identificar la zona problemática

  Escenario: Rastrear ubicación GPS en tiempo real
    Dado que el vehículo está en ruta
    Cuando consulto la ubicación actual
    Entonces veo las coordenadas GPS precisas
    Y veo la dirección aproximada en el mapa
    Y puedo comparar con la ruta planificada

  Escenario: Consultar tendencia de temperatura durante el viaje
    Dado que el viaje lleva 4 horas en tránsito
    Cuando consulto el gráfico de tendencia de temperatura
    Entonces veo la evolución de temperatura en las últimas 4 horas
    Y puedo identificar picos o caídas anormales
    Y los puntos críticos están marcados en el gráfico

  Escenario: Verificar que los datos se actualizan en tiempo real
    Dado que estoy viendo el dashboard en vivo
    Cuando el dispositivo envía una nueva lectura
    Entonces la interfaz se actualiza automáticamente en menos de 5 segundos
    Y no necesito refrescar la página manualmente
    Y veo un indicador visual de "datos actualizados"

  Escenario: Acceder al historial completo de una sesión de monitoreo
    Dado que una sesión de monitoreo está en curso
    Cuando solicito ver el historial detallado
    Entonces veo todas las lecturas de telemetría desde el inicio
    Y puedo filtrar por rango de fechas
    Y puedo exportar los datos en formato CSV

  Escenario: Recibir notificación cuando sesión se inicia correctamente
    Dado que programé un viaje con monitoreo
    Cuando el conductor inicia el viaje
    Entonces recibo una notificación de "Monitoreo iniciado"
    Y puedo ver la primera lectura de telemetría
    Y confirmo que el dispositivo está funcionando correctamente

  Escenario: Validar umbrales específicos por tipo de carga
    Dado que transporto productos farmacéuticos sensibles
    Y el rango permitido es muy estricto: 2°C a 4°C
    Cuando la temperatura alcanza 4.5°C
    Entonces el sistema genera alerta inmediata
    Y clasifica la alerta como crítica
    Y sugiere acciones correctivas basadas en el tipo de carga
