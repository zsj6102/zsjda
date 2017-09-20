package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 装修进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/17
 */
public class FitmentProcess implements Serializable {

    public String content;
    public String json;
    public int progress;
    public int auditstatus;
    public int aprovid;
    public int houseid;
    public int comunityid;
    public String depositsn;
    public String relcostsn;
    public String refund_reason;
    public int relcostspaystatus;
    public int depositpaystatus;
    public int decorateid;
    public int is_apply_leave;
    public List<Fitment> fitments = new ArrayList<>();

    @Override
    public String toString() {
        return "FitmentProcess{" +
                "content='" + content + '\'' +
                ", json='" + json + '\'' +
                ", progress=" + progress +
                ", auditstatus=" + auditstatus +
                ", aprovid=" + aprovid +
                ", houseid=" + houseid +
                ", comunityid=" + comunityid +
                ", fitments=" + fitments +
                '}';
    }
}
