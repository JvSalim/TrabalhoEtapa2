package Respostas.Q2.gRPC;

import grpc.calculadora.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClienteCalculadora {
    public static void main(String[] args) {
        ManagedChannel canal = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ServicoCalculadoraGrpc.ServicoCalculadoraBlockingStub stub = ServicoCalculadoraGrpc.newBlockingStub(canal);

        RequisicaoOperacao req = RequisicaoOperacao.newBuilder().setA(10).setB(2).build();

        System.out.println("Soma: " + stub.somar(req).getResultado());
        System.out.println("Subtração: " + stub.subtrair(req).getResultado());
        System.out.println("Multiplicação: " + stub.multiplicar(req).getResultado());
        System.out.println("Divisão: " + stub.dividir(req).getResultado());

        canal.shutdown();
    }
}
