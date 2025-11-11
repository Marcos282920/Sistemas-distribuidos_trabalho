# Sistema de Telefonia - Trabalho de Sistemas Distribuídos

## 📋 Descrição
Sistema de gerenciamento de telefonia com comunicação distribuída usando sockets TCP, streams personalizados e **serialização de objetos**.

## 🏗️ Estrutura do Projeto

```
src/
├── Main.java                      # Menu principal
├── TesteSocket.java               # Teste TCP automatizado
├── model/                         # POJOs e modelos
│   ├── Cliente.java              # POJO 1
│   ├── Fatura.java               # POJO 2
│   ├── Contrato.java             # POJO 3
│   ├── Linha.java                # Modelo 1
│   ├── GerenciadorLinhas.java    # Modelo 2
│   ├── MensagemRequest.java      # Protocolo Request
│   └── MensagemReply.java        # Protocolo Reply
├── stream/                        # Streams customizados
│   ├── FaturaOutputStream.java   # Subclasse OutputStream
│   └── FaturaInputStream.java    # Subclasse InputStream
└── network/                       # Comunicação TCP
    ├── ServidorTelefonia.java    # Servidor com serialização
    └── ClienteTelefonia.java     # Cliente com serialização
```

## 🚀 Como Executar

### Compilar
```powershell
cd src
javac model\*.java
javac -cp . stream\*.java
javac -cp . network\*.java
javac -cp . *.java
```

OU use o script:
```powershell
.\compilar.bat
```

### Testar

**Teste 1: Console (System.out)**
```powershell
cd src
echo "1" | java Main
```

**Teste 2: Arquivo (FileOutputStream)**
```powershell
cd src
echo "2" | java Main
```

**Teste 3: TCP Socket**
```powershell
cd src
java TesteSocket
```

## 🔌 Executar Cliente-Servidor Separadamente

**Terminal 1 - Servidor:**
```powershell
.\iniciar-servidor.bat
```

**Terminal 2 - Cliente:**
```powershell
.\iniciar-cliente.bat
```

## ✅ Requisitos Atendidos

### POJOs e Modelos
- ✅ 3 POJOs: Cliente, Fatura, Contrato (todos Serializable)
- ✅ 2 Classes de Modelo: Linha, GerenciadorLinhas

### Streams Customizados
- ✅ FaturaOutputStream (subclasse de OutputStream com construtor específico)
- ✅ FaturaInputStream (subclasse de InputStream)

### Testes de Stream
- ✅ Teste System.out (console)
- ✅ Teste FileOutputStream (arquivo)
- ✅ Teste TCP Socket

### Serialização de Objetos
- ✅ Cliente **EMPACOTA** request com ObjectOutputStream
- ✅ Cliente **DESEMPACOTA** reply com ObjectInputStream
- ✅ Servidor **DESEMPACOTA** request com ObjectInputStream
- ✅ Servidor **EMPACOTA** reply com ObjectOutputStream
- ✅ MensagemRequest e MensagemReply como objetos serializáveis

## 📦 Detalhes da Serialização

O sistema implementa empacotamento/desempacotamento de mensagens:

**Cliente:**
```java
// EMPACOTA request
ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
saida.writeObject(request);

// DESEMPACOTA reply
ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
MensagemReply reply = (MensagemReply) entrada.readObject();
```

**Servidor:**
```java
// DESEMPACOTA request
ObjectInputStream entrada = new ObjectInputStream(clientSocket.getInputStream());
MensagemRequest request = (MensagemRequest) entrada.readObject();

// EMPACOTA reply
ObjectOutputStream saida = new ObjectOutputStream(clientSocket.getOutputStream());
saida.writeObject(reply);
```

## 👥 Autor
Ulisses Alves
Marcos Eduardo 
