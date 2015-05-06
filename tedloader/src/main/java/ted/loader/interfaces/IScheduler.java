package ted.loader.interfaces;

import rx.Scheduler;

/**
 * Created by retor on 06.05.2015.
 */
public interface IScheduler {

    Scheduler getMain();
    Scheduler getBack();
}
