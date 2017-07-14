package scenery.scenery;

import java.io.Serializable;

/**
 * Created by Ivan on 7/14/2017.
 */


public class FilterItem implements Serializable {

    private String name;
    private boolean checked;

    public FilterItem(String name, boolean checked) {
        this.name = name;
        this.checked = checked;

    }

    public String getName() {
        return name;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean isChecked){
        this.checked = isChecked;
    }

}
