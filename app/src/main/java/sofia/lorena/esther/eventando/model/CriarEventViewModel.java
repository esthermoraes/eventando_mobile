package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import sofia.lorena.esther.eventando.R;

public class CriarEventViewModel extends AndroidViewModel {

    Integer selectedEventType = R.id.btnPresencial;

    public CriarEventViewModel(@NonNull Application application) {
        super(application);
    }

    public Integer getSelectedEventType() {
        return selectedEventType;
    }

    public void setSelectedEventType(Integer selectedEventType) {
        this.selectedEventType = selectedEventType;
    }
}
