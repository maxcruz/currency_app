## Currency App

Esta es una aplicaión de ejemplo que hace conversión de dólares estadounidenses a las diferentes monedas retornadas por el servicio de [Fixer](https://github.com/fixerAPI/fixer)

**Nota:** El servicio de Fixer está por cambiar el 1 de Junio de 2018  y va a ser necesario solitar un API-KEY y migrar la URL del servicio.

## Pantallas

### Mockups

### Screenshots

## Entorno de desarrollo
• Android Studio 3.2
• Gradle 4.6

## Arquitectura y librerías

### Presentación

* MVVM, para organizar la capa de presentación y mantenr el estado
* Lottie, una libreía de Airbnb para utilizar animaciones en la aplicaicón
* ButterKnife, para vincular los layout XML con las actividades
* ConstraintLayouts, para el diseño de los layouts
* JetPack Navigator, para la navegación. Presentado por Google en el IO de 2018 que viene siendo algo similar a los Storyboards de iOS.

### Capas
* RxJava, para comunicar las diferentes capas de la aplicación
* Dagger2, para mantener el principio de inversión de dependencias a lo largo de la aplicaión.
* Clean, los casos de uso se abstraen en interactuadores que no dependen de Android
* Lifecycle Aware Components, presentado en la IO de 2017 dentro de los componentes de arquitectura de Google. Se usan ViewModel, LiveData y Lifecycle Observers

### Datos
* Repository, el acceso a datos se abstrae y se maneja en una capa aparte
* Room como motor de persistencia (también presentado en la IO de 2017)
* Estrategia de Off-Line, primero consume el servicio remoto y luego va al caché local
* Retrofit (RxJava adapter, Gson), para la comunicaión con el servicio web

### Pruebas
* JUnit4, para las pruebas unitarias
* Mockito, para la genración de mocks
* Espresso, para las pruebas con el framework
* MockWebServer, para probar los servicios de manera isolada

## Pendientes

• Migrar el API al nuevo servicio de Fixer
* Más pruebas en todas las capas pero principalmente en la de UI
• Sería genial poder cambiar la moneda base
• Modificar el orden de las monedas (o seleccionar unas favoritas)
* Incluir otras fuentes (criptomonedas por ejemplo)

## Licencia
```
The MIT License (MIT)

Copyright (c) 2011-2018 Twitter, Inc.
Copyright (c) 2011-2018 The Bootstrap Authors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
