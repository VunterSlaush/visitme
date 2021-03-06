package visit.me.gil.mota.visitme.useCases;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import visit.me.gil.mota.visitme.managers.RequestManager;
import visit.me.gil.mota.visitme.managers.UserManager;
import visit.me.gil.mota.visitme.models.Community;
import visit.me.gil.mota.visitme.utils.Functions;

/**
 * Created by mota on 17/4/2018.
 */

public class GetUserCommunities extends UseCase implements Observer<JSONObject> {

    public GetUserCommunities(Result result) {
        super(result);
    }

    @Override
    public void run() {
        RequestManager.getInstance().getUserCommunities().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject jsonObject) {
        onSuccess(jsonObject);
    }

    private void onSuccess(JSONObject obj)
    {
        if (obj.has("communities"))
        {
            UserManager.getInstance().saveCommunities(obj);
            if (resultSetter != null)
                resultSetter.onSuccess();
        }
        else
        {
            if (resultSetter != null)
                resultSetter.onError("Error inesperado");
        }


    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
