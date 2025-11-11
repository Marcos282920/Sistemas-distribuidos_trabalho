# Sistema de Telefonia - Trabalho de Sistemas Distribuídos

## 📋 Descrição
Sistema de gerenciamento de telefonia com comunicação distribuída usando sockets TCP e streams personalizados.

## 🏗️ Estrutura do Projeto

```
src/
├── Main.java                      # Menu principal
├── TesteSocket.java               # Teste TCP automatizado
├── ClienteSimples.java            # Cliente simples
├── model/                         # POJOs e modelos
│   ├── Cliente.java              # POJO 1
│   ├── Fatura.java               # POJO 2
│   ├── Contrato.java             # POJO 3
│   ├── Linha.java                # Modelo 1
│   └── GerenciadorLinhas.java    # Modelo 2
├── stream/                        # Streams customizados
│   ├── FaturaOutputStream.java   # Subclasse OutputStream ⭐
│   └── FaturaInputStream.java    
└── network/                       # Comunicação TCP
    ├── ServidorTelefonia.java    
    └── ClienteTelefonia.java     
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

- ✅ 3 POJOs: Cliente, Fatura, Contrato
- ✅ 2 Classes de Modelo: Linha, GerenciadorLinhas
- ✅ FaturaOutputStream (subclasse de OutputStream)
- ✅ Teste System.out
- ✅ Teste FileOutputStream
- ✅ Teste TCP Socket

## 👥 Autor
Ulisses - Sistemas Distribuídos 2025