package ch.chiodo.sssync.sync.network;

import java.util.Observable;
import java.util.Observer;

public class NullObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        // The NullObserver delivers "nothing" behaviour to avoid unexpected failures
    }
}
