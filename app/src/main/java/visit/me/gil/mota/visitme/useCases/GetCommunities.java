package visit.me.gil.mota.visitme.useCases;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import visit.me.gil.mota.visitme.managers.RequestManager;
import visit.me.gil.mota.visitme.managers.UserManager;
import visit.me.gil.mota.visitme.models.Community;
import visit.me.gil.mota.visitme.utils.Functions;

public class GetCommunities extends UseCase implements Observer<JSONArray> {

    private Result result;
    public GetCommunities(Result result) {
        super(null);
        this.result = result;
    }

    @Override
    public void run() {
        RequestManager.getInstance().getCommunities().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONArray arry) {
        Community [] asArry = Functions.parse(arry, Community[].class);
        result.onCommunities(Arrays.asList(asArry));
    }


    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public interface Result extends UseCase.Result
    {
        void onCommunities(List<Community> communities);
    }
}
