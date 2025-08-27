# WeatherApp ☁️

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/ocesar9/PDM-WeatherApp/blob/main/LICENSE)
[![Firebase](https://img.shields.io/badge/weatherapp-ae957)]()


## 📋 Sobre o Projeto

**WeatherApp** é um aplicativo Android nativo que oferece **informações meteorológicas em tempo real integradas ao mapa**. O aplicativo utiliza geolocalização para exibir dados climáticos precisos de qualquer localização, permitindo que os usuários visualizem condições meteorológicas diretamente no mapa interativo e recebam notificações personalizadas para suas cidades favoritas.

## ✨ Funcionalidades

- 🗺️ **Mapa Interativo** - Visualização climática integrada ao Google Maps
- 📍 **Geolocalização** - Detecção automática da localização atual
- 🎯 **Clima por Toque** - Clique em qualquer cidade para ver o clima
- 📱 **Interface Nativa** - Performance otimizada para Android
- ⭐ **Cidades Favoritas** - Marque locais importantes para acesso rápido
- 📊 **Previsão Estendida** - Dados meteorológicos para próximos dias
- 🔔 **Notificações Push** - Alertas automáticos de mudanças climáticas
- 🌡️ **Dados Detalhados** - Temperatura, umidade, vento e pressão
- 📋 **Lista de Cidades** - Gerenciamento fácil de locais salvos
- 🔄 **Atualizações Automáticas** - Dados sempre atualizados

## 📱 Layout Mobile

### Tela Principal com Mapa
Interface principal mostrando o mapa interativo com indicadores climáticos e a localização atual do usuário.

<div align="center">
  <img src="https://github.com/ocesar9/PDM-WeatherApp/blob/main/images/mobile-1.png" alt="Tela Principal - Mapa Interativo" width="300">
</div>

### Detalhes do Clima
Tela de informações detalhadas de uma cidade selecionada, exibindo temperatura atual, condições meteorológicas e ícone representativo.

<div align="center">
  <img src="https://github.com/ocesar9/PDM-WeatherApp/blob/main/images/mobile-2.png" alt="Detalhes do Clima" width="300">
</div>

### Previsão Estendida
Interface mostrando a previsão meteorológica para os próximos dias com temperaturas máximas, mínimas e condições climáticas.

<div align="center">
  <img src="https://github.com/ocesar9/PDM-WeatherApp/blob/main/images/mobile-3.png" alt="Previsão Estendida" width="300">
</div>

### Lista de Cidades Favoritas
Tela de gerenciamento das cidades marcadas como favoritas, com opções de configuração de notificações e acesso rápido.

<div align="center">
  <img src="https://github.com/ocesar9/PDM-WeatherApp/blob/main/images/mobile-4.png" alt="Cidades Favoritas" width="300">
</div>

## 🚀 Tecnologias Utilizadas

### Frontend (Android)
- **[Kotlin](https://kotlinlang.org/)** - Linguagem oficial para desenvolvimento Android
- **[Retrofit](https://square.github.io/retrofit/)** - Cliente HTTP para consumo de APIs REST
- **[Glide](https://github.com/bumptech/glide)** - Carregamento e cache eficiente de imagens
- **[Android Architecture Components](https://developer.android.com/topic/architecture)** - ViewModel, LiveData, Navigation
- **[Material Design](https://material.io/develop/android)** - Componentes de interface seguindo guidelines do Google

### Backend & Serviços
- **[Firebase](https://firebase.google.com/)** - Backend as a Service (BaaS)
  - **Firebase Cloud Messaging** - Sistema de notificações push
  - **Firebase Analytics** - Análise de uso do aplicativo
- **[Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk)** - Integração com mapas interativos
- **[OpenWeatherMap API](https://openweathermap.org/api)** - Dados meteorológicos em tempo real
- **[Android Location Services](https://developer.android.com/training/location)** - Geolocalização e GPS

### Arquitetura & Padrões
- **MVVM (Model-View-ViewModel)** - Padrão arquitetural para separação de responsabilidades
- **Repository Pattern** - Abstração da camada de dados
- **Dependency Injection** - Injeção de dependências com Hilt/Dagger
- **Coroutines** - Programação assíncrona em Kotlin

## 📦 Como Executar o Projeto

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- SDK Android 21+ (Android 5.0 Lollipop)
- Chave da API OpenWeatherMap
- Projeto configurado no Firebase

### Configuração das APIs
```kotlin
// 1. Obtenha uma chave gratuita em: https://openweathermap.org/api
// 2. Configure o Firebase Console para seu projeto
// 3. Baixe o arquivo google-services.json
// 4. Adicione as chaves no arquivo local.properties
```

### Instalação
```bash
# Clone o repositório
git clone https://github.com/ocesar9/PDM-WeatherApp

# Abra o projeto no Android Studio
# Configure as variáveis no local.properties:
WEATHER_API_KEY=sua_chave_openweather
MAPS_API_KEY=sua_chave_google_maps

# Sincronize o projeto (Sync Now)
# Execute no dispositivo ou emulador
```

### Permissões Necessárias
```xml
<!-- Localização -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- Internet -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- Notificações -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

## 🎯 Conceitos Aplicados

- **Android Jetpack** - Conjunto de bibliotecas para desenvolvimento robusto
- **Reactive Programming** - LiveData e Observer pattern
- **RESTful API Consumption** - Integração com APIs externas
- **Geolocation Services** - Uso de GPS e serviços de localização
- **Push Notifications** - Sistema de notificações em background
- **Image Caching** - Otimização de carregamento de imagens
- **Fragment Navigation** - Navegação moderna entre telas
- **Material Design** - Interface seguindo design system do Google
- **Background Processing** - Workers para atualizações periódicas

## 🌟 Funcionalidades Avançadas

### Sistema de Notificações
- **Alertas Personalizados** - Configure notificações por cidade
- **Background Updates** - Atualizações automáticas em segundo plano
- **Threshold Alerts** - Alertas baseados em mudanças de temperatura

### Integração com Mapas
- **Markers Customizados** - Ícones meteorológicos no mapa
- **Cluster Management** - Agrupamento de marcadores próximos
- **Offline Support** - Cache de dados para uso offline

### Performance
- **Image Optimization** - Carregamento eficiente de ícones climáticos
- **Data Caching** - Armazenamento local para reduzir requisições
- **Background Sync** - Sincronização inteligente de dados

## 📱 Compatibilidade

- **Android 5.0+** (API level 21+)
- **Arquiteturas:** ARM, ARM64, x86, x86_64
- **Orientação:** Portrait e Landscape
- **Idiomas:** Português (BR), Inglês

## 📄 Licença

Este projeto está sob licença MIT. Veja o arquivo [LICENSE](https://github.com/ocesar9/PDM-WeatherApp/blob/main/LICENSE) para mais detalhes.

## 👨‍💻 Autor

**Júlio Guimarães**
- GitHub: [@ocesar9](https://github.com/ocesar9)
- LinkedIn: [Seu perfil LinkedIn](https://www.linkedin.com/in/j%C3%BAlio-guimar%C3%A3es-183110162/)

---

⭐ Se este projeto te ajudou, deixe uma estrela no repositório!
