package rmi;

import java.rmi.RemoteException;

public interface ChatClient {
    void receiveMessage(String message) throws RemoteException;
}
