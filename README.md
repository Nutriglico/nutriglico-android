# NutriGlico

## Visão Geral
O **NutriGlico** é um aplicativo Android desenvolvido para auxiliar pacientes e nutricionistas no controle e monitoramento de glicemia. Ele oferece funcionalidades intuitivas e práticas para gerenciar registros de saúde e promover um acompanhamento mais eficiente e personalizado.

---

## Funcionalidades Principais

### **Autenticação e Cadastro**
- Login de usuários com validação de credenciais.
- Cadastro de pacientes e nutricionistas com campos específicos (ex.: CRN para nutricionistas).
- Recuperação de senha.

### **Gestão de Glicemia**
- Registro de medições glicêmicas com opções de data, tipo de medição (jejum, aleatória) e nível de glicemia.
- Exibição de histórico com gráficos intuitivos e lista detalhada.
- Classificação por status (NORMAL, ALERT, CRITICAL) com cores indicativas.

### **Perfil do Usuário**
- Visualização de informações básicas do perfil.
- Edição de dados, como nome, e-mail e CPF.
- Opção para exclusão de conta com confirmação.

### **Outras Funcionalidades**
- Home personalizada com informações de medições, refeições e medicamentos.
- Navegação fluida entre as seções do app através de barra inferior e menu principal.

---

## Tecnologias Utilizadas

### **Linguagens e Frameworks**
- Kotlin
- Android Jetpack (ViewModel, LiveData, Navigation, Room, etc.)

### **Arquitetura**
- Clean Architecture com separação em camadas: Data, Domain e UI.
- Modularização por features, facilitando escalabilidade e manutenção.

### **Injeção de Dependências**
- Koin para gerenciamento de dependências.

### **Testes**
- Testes unitários e de UI para garantir a qualidade do código.
- Frameworks utilizados:
  - MockK para criação de mocks nos testes unitários.
  - JUnit
  - Espresso

---

## Estrutura do Projeto

```plaintext
nutriglico
├── commons
│   ├── model
│   ├── network
│   ├── ui
│   └── util
├── features
│   ├── glicemiccontrol
│   │   ├── di
│   │   ├── history
│   │   └── register
│   ├── home
│   ├── menu
│   └── usermanagement
│       ├── auth
│       ├── profile
│       └── signup
└── ui
    └── theme
```

---

## Adesão à Arquitetura MVVM
O projeto segue a arquitetura **MVVM** (Model-View-ViewModel):

- **Model**:
  - Localizada em camadas como `data` (repositórios, serviços e modelos de dados).
  - Responsável por lidar com a lógica de dados e fornecer as informações para a camada de domínio ou ViewModel.

- **View**:
  - Representada pelas telas e componentes em `ui`, como `screen` e `components`.
  - Responsável por renderizar os dados fornecidos pelo ViewModel e capturar interações do usuário.

- **ViewModel**:
  - Localizada em `ui.viewmodel`.
  - Atua como intermediário entre a View e o Model, fornecendo dados e estados prontos para exibição, enquanto gerencia a lógica de negócios específica da interface.

**Evidências de aderência**:
- Uso de **LiveData** (ou equivalente) para comunicar alterações de estado da ViewModel para a View.
- Separação clara entre a lógica de apresentação (ViewModel) e a lógica de dados (Model).

---

## Adesão aos Princípios SOLID
### **S - Single Responsibility Principle (SRP)**
- Cada classe tem uma única responsabilidade bem definida. Por exemplo:
  - Repositórios (`RepositoryImpl`) são responsáveis por interagir com APIs ou bancos de dados.
  - ViewModels gerenciam apenas a lógica de apresentação.
  - Componentes de UI em `commons.ui` são reutilizáveis e focados na interface.

### **O - Open/Closed Principle (OCP)**
- O uso de interfaces para repositórios e serviços permite a extensão sem modificação. Por exemplo:
  - `GlicemicHistoryRepository` pode ser implementado por diferentes fontes de dados.

### **L - Liskov Substitution Principle (LSP)**
- As interfaces permitem substituir implementações (ex.: `GlicemicHistoryRepositoryImpl` pode substituir `GlicemicHistoryRepository` sem causar problemas, pois respeitam o contrato definido pela interface).

### **I - Interface Segregation Principle (ISP)**
- Interfaces são granulares e focadas em responsabilidades específicas.

### **D - Dependency Inversion Principle (DIP)**
- A injeção de dependências é utilizada através de Koin, promovendo o desacoplamento de classes concretas.

---

## Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/nutriglico.git
   ```

2. Abra o projeto no Android Studio.

3. Sincronize as dependências do Gradle.

4. Conecte um dispositivo ou inicie um emulador.

5. Execute o aplicativo:
   ```bash
   Run > Run 'app'
   ```

---

## Licença
Este projeto está licenciado sob a [MIT License](LICENSE).
