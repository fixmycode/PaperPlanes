package cl.blackbird.paper.data;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {
    public abstract void setId(int id);
    public abstract Integer getId();
    public abstract void update(BaseModel model);
}
