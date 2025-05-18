package Respostas.Q2.gRPC;

import grpc.calculadora.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class ServidorCalculadora {

    public static void main(String[] args) throws Exception {
        Server servidor = ServerBuilder.forPort(50051)
                .addService(new ImplementacaoCalculadora())
                .build();

        servidor.start();
        System.out.println("Servidor gRPC em execução.");
        servidor.awaitTermination();
    }

    static class ImplementacaoCalculadora extends ServicoCalculadoraGrpc.ServicoCalculadoraImplBase {

        public void somar(RequisicaoOperacao req, StreamObserver<RespostaOperacao> res) {
            double resultado = req.getA() + req.getB();
            res.onNext(RespostaOperacao.newBuilder().setResultado(resultado).build());
            res.onCompleted();
        }

        public void subtrair(RequisicaoOperacao req, StreamObserver<RespostaOperacao> res) {
            double resultado = req.getA() - req.getB();
            res.onNext(RespostaOperacao.newBuilder().setResultado(resultado).build());
            res.onCompleted();
        }

        public void multiplicar(RequisicaoOperacao req, StreamObserver<RespostaOperacao> res) {
            double resultado = req.getA() * req.getB();
            res.onNext(RespostaOperacao.newBuilder().setResultado(resultado).build());
            res.onCompleted();
        }

        public void dividir(RequisicaoOperacao req, StreamObserver<RespostaOperacao> res) {
            if (req.getB() == 0) {
                res.onError(new ArithmeticException("Divisão por zero!"));
                return;
            }
            double resultado = req.getA() / req.getB();
            res.onNext(RespostaOperacao.newBuilder().setResultado(resultado).build());
            res.onCompleted();
        }
    }
}
