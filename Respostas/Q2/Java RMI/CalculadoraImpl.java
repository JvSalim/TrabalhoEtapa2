import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CalculadoraImpl extends UnicastRemoteObject implements Calculadora {

    protected CalculadoraImpl() throws RemoteException {
        super();
    }

    public double somar(double a, double b) { return a + b; }
    public double subtrair(double a, double b) { return a - b; }
    public double multiplicar(double a, double b) { return a * b; }
    public double dividir(double a, double b) {
        if (b == 0) throw new ArithmeticException("Divisão por zero!");
        return a / b;
    }
}
