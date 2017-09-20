package com.colpencil.propertycloud.View.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Fitment;
import com.colpencil.propertycloud.Bean.FitmentProcess;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.AdviceListActivity;
import com.colpencil.propertycloud.View.Activitys.Home.DecorateApplyActivity;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.Home.MaterialManagementActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PaySureActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ReadActivity;
import com.colpencil.propertycloud.View.Activitys.Home.RegistryFormListActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.CloseManager.FitmentProcessFragment;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.yinghe.whiteboardlib.bean.Image;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 装修进度
 * @Email DramaScript@outlook.com
 * @date 2016/11/17
 */
public class FitmentAdapter extends BaseExpandableListAdapter {

    private List<FitmentProcess> processList;

    private Activity context;
    private Intent intent;

    private FitmentProcessFragment fragment;

    public static final int REQUEST_CODE_SELECT = 100;

    public FitmentAdapter(List<FitmentProcess> processList, Activity context, FitmentProcessFragment fragment) {
        this.processList = processList;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public int getGroupCount() {
        return processList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return processList.get(groupPosition).fitments.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return processList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return processList.get(groupPosition).fitments.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_fitment_group, null);
            viewHolder = new GroupViewHolder();
            viewHolder.gr_vv1 = convertView.findViewById(R.id.gr_vv1);
            viewHolder.gr_vv2 = convertView.findViewById(R.id.gr_vv2);
            viewHolder.iv_yuan = (ImageView) convertView.findViewById(R.id.iv_yuan);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_clear = (TextView) convertView.findViewById(R.id.tv_clear);
            viewHolder.tv_reson = (TextView) convertView.findViewById(R.id.tv_reson);
            viewHolder.ll_reson = (LinearLayout) convertView.findViewById(R.id.ll_reson);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        FitmentProcess fitmentProcess = processList.get(groupPosition);
        viewHolder.tv_name.setText(fitmentProcess.content);
        if (groupPosition == 0) {
            viewHolder.gr_vv1.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.gr_vv1.setVisibility(View.VISIBLE);
        }
        int progress = fitmentProcess.progress;
        switch (progress) {
            case 0:
                if (groupPosition == 0) {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_green));
                    viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                } else {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_gre));
                    viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                }
                if (groupPosition == 1) {
                    viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                } else {
                    viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                }
                break;
            case 1:
                if (groupPosition <= progress) {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_green));
                    if (groupPosition == 0) {
                        viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                    }

                } else {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_gre));
                    viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                }
                if (groupPosition <= progress) {
                    viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                } else {
                    viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                }
                if (groupPosition == 1) {
                    if (processList.get(0).auditstatus == 2) {
                        viewHolder.gr_vv2.setVisibility(View.INVISIBLE);
                        viewHolder.ll_reson.setVisibility(View.VISIBLE);
                        viewHolder.tv_clear.setVisibility(View.VISIBLE);
                        viewHolder.tv_clear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: 2016/12/1 清除记录
                                disFitment(processList.get(groupPosition).aprovid);
                            }
                        });
                        viewHolder.tv_reson.setText(processList.get(0).refund_reason);
                    }
                } else {
                    viewHolder.gr_vv2.setVisibility(View.VISIBLE);
                    viewHolder.ll_reson.setVisibility(View.GONE);
                    viewHolder.tv_clear.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (groupPosition <= progress) {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_green));
                    if (groupPosition <= progress) {
                        viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                    }

                } else {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_gre));
                    viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                }
                if (groupPosition <= progress) {
                    viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                } else {
                    if (groupPosition == 3) {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                    }
                }
                break;
            case 3:
                if (groupPosition <= progress) {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_green));

                    if (groupPosition <= progress) {
                        viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                    }

                } else {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_gre));
                    viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                }
                if (groupPosition <= progress) {
                    viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                } else {
                    if (groupPosition == 4) {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                    }
                }

                break;
            case 4:
                if (groupPosition <= progress) {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_green));
                    if (groupPosition <= progress) {
                        viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                    }

                } else {
                    viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_gre));
                    viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                }
                if (groupPosition <= progress) {
                    viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                } else {
                    if (groupPosition == 5) {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_gre));
                    }
                }
                break;
            case 5:
                viewHolder.iv_yuan.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_green));
                viewHolder.gr_vv2.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                viewHolder.gr_vv1.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                break;
        }
        if (progress > 3) {
            if (groupPosition == 3) {
                viewHolder.tv_name.setText("已领取装修许可证");
            }
        }else {
            if (groupPosition == 3) {
                viewHolder.tv_name.setText("待领取装修许可证");
            }
        }
        if (progress >= 1) {
            if (groupPosition == 1) {
                switch (processList.get(0).auditstatus) {
                    case -1:
                        viewHolder.tv_name.setText("待提交");
                        break;
                    case 0:
                        viewHolder.tv_name.setText("待审核");
                        break;
                    case 1:
                        viewHolder.tv_name.setText("审核已通过");
                        break;
                    case 2:
                        viewHolder.tv_name.setText("审核已拒绝");
                        break;
                    case 3:
                        viewHolder.tv_name.setText("审核已取消");
                        break;
                }
            }
        }
        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getChildView(int groupPosition, int childPosition, final boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_fitment_child, null);
            viewHolder = new ChildViewHolder();
            viewHolder.chil_vv = convertView.findViewById(R.id.chil_vv);
            viewHolder.iv_check = (ImageView) convertView.findViewById(R.id.iv_check);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            viewHolder.rl_ok = (RelativeLayout) convertView.findViewById(R.id.rl_ok);
            viewHolder.chil_vv3 = convertView.findViewById(R.id.chil_vv3);
            viewHolder.tv_change = (TextView) convertView.findViewById(R.id.tv_change);
            viewHolder.tv_dis = (TextView) convertView.findViewById(R.id.tv_dis);
            viewHolder.tv_submit = (TextView) convertView.findViewById(R.id.tv_submit);

            viewHolder.ll_wu = (LinearLayout) convertView.findViewById(R.id.ll_wu);
            viewHolder.tv_jian = (TextView) convertView.findViewById(R.id.tv_jian);
            viewHolder.tv_yijian = (TextView) convertView.findViewById(R.id.tv_yijian);
            viewHolder.tv_li = (TextView) convertView.findViewById(R.id.tv_li);
            viewHolder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        final FitmentProcess fitmentProcess = processList.get(groupPosition);
        final Fitment fitment = fitmentProcess.fitments.get(childPosition);
        int progress = fitmentProcess.progress;
        if (groupPosition == 2) { // 去支付
            viewHolder.ll_wu.setVisibility(View.GONE);
            if (processList.get(0).progress >= 2) {
                if (childPosition == 0) {
                    ColpencilLogger.e("depositpaystatus=" + processList.get(0).depositpaystatus + ",childPosition=" + childPosition);
                    if (processList.get(0).depositpaystatus == 0 || processList.get(0).depositpaystatus == -1) {  // 未支付
                        viewHolder.iv_check.setBackground(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                    } else {
                        viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                    }
                    if (processList.get(0).depositpaystatus == 1) { // 已经支付了
                        viewHolder.tv_state.setVisibility(View.INVISIBLE);
                    } else {
                        viewHolder.tv_state.setVisibility(View.VISIBLE);
                        viewHolder.tv_state.setText("去支付");
                        viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent = new Intent(context, PaySureActivity.class);
                                intent.putExtra("type", 1);
                                intent.putExtra("billIds", processList.get(0).depositsn);
                                context.startActivity(intent);
                                ColpencilLogger.e("depositsn=" + processList.get(0).relcostsn);
                            }
                        });
                    }
                } else {
                    if (processList.get(0).relcostspaystatus == 0 || processList.get(0).relcostspaystatus == -1) {
                        viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                    } else {
                        viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                    }
                    if (processList.get(0).relcostspaystatus == 1) { // 已经支付了
                        viewHolder.tv_state.setVisibility(View.INVISIBLE);
                    } else {
                        viewHolder.tv_state.setVisibility(View.VISIBLE);
                        viewHolder.tv_state.setText("去支付");
                        viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent = new Intent(context, PaySureActivity.class);
                                intent.putExtra("type", 1);
                                intent.putExtra("billIds", processList.get(0).relcostsn);
                                context.startActivity(intent);
                                ColpencilLogger.e("relcostsn=" + processList.get(0).depositsn);
                            }
                        });
                    }
                }
            } else {
                viewHolder.tv_state.setVisibility(View.INVISIBLE);
            }

        } else if (groupPosition == 4) { // 装修中

            if (processList.get(0).progress >= 4) {
                viewHolder.tv_li.setVisibility(View.VISIBLE);
                viewHolder.ll_wu.setVisibility(View.VISIBLE);
                viewHolder.rl.setVisibility(View.GONE);
                viewHolder.tv_yijian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/12/1 物业意见
                        intent = new Intent(context, AdviceListActivity.class);
                        intent.putExtra("aprovid", processList.get(0).aprovid);
                        context.startActivity(intent);
                    }
                });
                viewHolder.tv_jian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/12/1 监理日记
                        intent = new Intent(context, LoadUriActivity.class);
                        intent.putExtra("url", "");
                        intent.putExtra("title", "监理日记");
                        context.startActivity(intent);
                    }
                });
                if (processList.get(0).is_apply_leave == 1) {  // 已经申请离场
                    viewHolder.tv_li.setVisibility(View.GONE);
                } else {
                    viewHolder.tv_li.setVisibility(View.VISIBLE);
                    viewHolder.tv_li.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 2016/12/1 申请离场
                            applaLeave(processList.get(0).decorateid + "");
                        }
                    });
                }
            } else {
                viewHolder.ll_wu.setVisibility(View.GONE);
                viewHolder.rl.setVisibility(View.VISIBLE);
            }

        } else if (groupPosition == 0) { // 签署材料
            if (progress == 0) {
                viewHolder.tv_state.setVisibility(View.VISIBLE);
                switch (childPosition) {
                    case 0:
                        if (fitment.regulation == 1) {
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(context, ReadActivity.class);
                                    intent.putExtra("flag", 1);
                                    intent.putExtra("type", 1);
                                    intent.putExtra("name", "装修管理规定");
                                    intent.putExtra("community_id", processList.get(0).comunityid + "");
                                    intent.putExtra("aprovid", processList.get(0).aprovid);
                                    intent.putExtra("houseid", processList.get(0).houseid);
                                    context.startActivity(intent);
                                }
                            });
                            viewHolder.tv_state.setText("修改");
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                        } else {
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(context, ReadActivity.class);
                                    intent.putExtra("flag", 1);
                                    intent.putExtra("type", 1);
                                    intent.putExtra("name", "装修管理规定");
                                    intent.putExtra("community_id", processList.get(0).comunityid + "");
                                    intent.putExtra("aprovid", processList.get(0).aprovid);
                                    intent.putExtra("houseid", processList.get(0).houseid);
                                    context.startActivity(intent);
                                }
                            });
                            viewHolder.tv_state.setText("填写");
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                        }
                        break;
                    case 1:
                        if (fitment.promise == 1) {
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(context, ReadActivity.class);
                                    intent.putExtra("flag", 2);
                                    intent.putExtra("type", 1);
                                    intent.putExtra("name", "装修承诺书");
                                    intent.putExtra("community_id", processList.get(0).comunityid + "");
                                    intent.putExtra("aprovid", processList.get(0).aprovid);
                                    intent.putExtra("houseid", processList.get(0).houseid);
                                    ColpencilLogger.e("aprovid1=" + processList.get(0).aprovid);
                                    context.startActivity(intent);
                                }
                            });
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                            viewHolder.tv_state.setText("修改");
                        } else {
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(context, ReadActivity.class);
                                    intent.putExtra("flag", 2);
                                    intent.putExtra("type", 1);
                                    intent.putExtra("name", "装修承诺书");
                                    intent.putExtra("community_id", processList.get(0).comunityid + "");
                                    intent.putExtra("aprovid", processList.get(0).aprovid);
                                    intent.putExtra("houseid", processList.get(0).houseid);
                                    ColpencilLogger.e("aprovid1=" + processList.get(0).aprovid);
                                    context.startActivity(intent);
                                }
                            });
                            viewHolder.tv_state.setText("填写");
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                        }
                        break;
                    case 2: // 装修申请表
                        if (fitment.application == 1) {
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                            viewHolder.tv_state.setText("修改");
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(context, DecorateApplyActivity.class);
                                    intent.putExtra("type", 2);
                                    intent.putExtra("aprovid", processList.get(0).aprovid);
                                    intent.putExtra("houseid", processList.get(0).houseid);
                                    context.startActivity(intent);
                                }
                            });
                        } else {
                            viewHolder.tv_state.setText("填写");
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(context, DecorateApplyActivity.class);
                                    intent.putExtra("type", 1);
                                    intent.putExtra("aprovid", processList.get(0).aprovid);
                                    intent.putExtra("houseid", processList.get(0).houseid);
                                    context.startActivity(intent);
                                }
                            });
                        }

                        break;
                    case 3:// 物业公司资质扫描
                        if (fitment.aptitude == 1) {
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                            viewHolder.tv_state.setText("修改");
                        } else {
                            viewHolder.tv_state.setText("填写");
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                        }
                        viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragment.selImageList.clear();
                                intent = new Intent(context, ImageGridActivity.class);
                                fragment.flag = false;
                                fragment.startActivityForResult(intent, REQUEST_CODE_SELECT);
                            }
                        });
                        break;
                    case 4:// 装修设计资料扫描
                        if (fitment.design == 1) {
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                            viewHolder.tv_state.setText("修改");
                        } else {
                            viewHolder.tv_state.setText("填写");
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                        }
                        viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragment.selImageList2.clear();
                                intent = new Intent(context, ImageGridActivity.class);
                                fragment.flag = true;
                                fragment.startActivityForResult(intent, REQUEST_CODE_SELECT);
                            }
                        });
                        break;
                    case 5:
                        if (fitment.personreg == 1) {
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
                            viewHolder.tv_state.setText("修改");
                            viewHolder.rl_ok.setVisibility(View.VISIBLE);
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO: 2016/11/21 修改
                                    Intent intent = new Intent(context, RegistryFormListActivity.class);
                                    intent.putExtra("approveid", fitmentProcess.aprovid);
                                    intent.putExtra("type", 1); // 0 是新增  1 ：修改
                                    context.startActivity(intent);
                                }
                            });
                        } else {
                            viewHolder.rl_ok.setVisibility(View.GONE);
                            viewHolder.tv_state.setText("填写");
                            viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
                            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO: 2016/11/21 去填写
                                    Intent intent = new Intent(context, RegistryFormListActivity.class);
                                    intent.putExtra("approveid", fitmentProcess.aprovid);
                                    intent.putExtra("type", 0); // 0 是新增  1 ：修改
                                    context.startActivity(intent);
                                }
                            });
                        }
                        break;
                }
            } else {
                viewHolder.rl.setVisibility(View.VISIBLE);
                viewHolder.tv_state.setVisibility(View.GONE);
                viewHolder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
            }

        } else {
            viewHolder.ll_wu.setVisibility(View.GONE);
            viewHolder.tv_state.setVisibility(View.VISIBLE);
            viewHolder.tv_state.setText("查看");
        }
        if (groupPosition == 0 && childPosition == 5) {
            if (progress == 0) { // 只有进度是0的时候才显示
                viewHolder.rl_ok.setVisibility(View.VISIBLE);
                if (fitment.regulation == 1 && fitment.design == 1 && fitment.application == 1 && fitment.aptitude == 1 && fitment.promise == 1 && fitment.personreg == 1) {
                    //这个时候说明5项都已经提交了 ，可以确定提交装修了
                    viewHolder.tv_submit.setBackground(context.getResources().getDrawable(R.drawable.rect_blue));
                    viewHolder.tv_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 2016/11/21 确认提交装修
                            confirmDecortApprove(fitmentProcess.aprovid);
                        }
                    });
                }
                viewHolder.tv_dis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/11/21 取消本次装修
                        disFitment(fitmentProcess.aprovid);
                    }
                });
                viewHolder.tv_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/11/21 关联的东西需要更改
                        intent = new Intent(context, MaterialManagementActivity.class);
                        intent.putExtra("type", 1);
                        context.startActivity(intent);
                    }
                });
            } else {
                viewHolder.rl_ok.setVisibility(View.GONE);
            }

        } else {
            viewHolder.rl_ok.setVisibility(View.GONE);
        }
        switch (progress) {
            case 0:
                if (groupPosition == 0) {
                    viewHolder.chil_vv.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                }
                break;
            case 1:
                if (groupPosition <= progress) {
                    viewHolder.chil_vv.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                }
                break;
            case 2:
                if (groupPosition <= progress) {
                    viewHolder.chil_vv.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                }
                break;
            case 3:
                if (groupPosition <= progress) {
                    viewHolder.chil_vv.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                }
                break;
            case 4:
                if (groupPosition <= progress) {
                    viewHolder.chil_vv.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                }
                break;
            case 5:
                viewHolder.chil_vv.setBackgroundColor(context.getResources().getColor(R.color.main_blue));
                break;
        }
        if (groupPosition == 2) {
            if (childPosition == 0) {
                viewHolder.tv_content.setText("  装修管理费" + fitment.deposit + "元");
            } else {
                viewHolder.tv_content.setText("  装修押金" + fitment.relcosts + "元");
            }
        } else {
            viewHolder.tv_content.setText(fitment.name);
        }
        return convertView;
    }

    /**
     * 申请离场
     */
    private void applaLeave(String decorate_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("decorate_id", decorate_id);
        RetrofitManager.getInstance(1, context, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .applyLeave(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        String message = resultInfo.message;
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                ToastTools.showShort(context, true, "申请成功！");
                                break;
                            case 1:
                                ToastTools.showShort(context, false, message);
                                break;
                            case 3:
                                context.startActivity(new Intent(context, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 确认提交申请
     *
     * @param aprovid
     */
    private void confirmDecortApprove(int aprovid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, context, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .confirmDecortApprove(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        String message = resultInfo.message;
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                ToastTools.showShort(context, true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(context, false, message);
                                break;
                            case 3:
                                context.startActivity(new Intent(context, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 取消装修
     *
     * @param aprovid
     */
    private void disFitment(int aprovid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, context, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .cancelDecortApprove(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        String message = resultInfo.message;
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                ToastTools.showShort(context, true, "取消成功！");
                                break;
                            case 1:
                                ToastTools.showShort(context, false, message);
                                break;
                            case 3:
                                context.startActivity(new Intent(context, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        public View gr_vv1;
        public View gr_vv2;
        public ImageView iv_yuan;
        public TextView tv_name;
        public TextView tv_clear;
        public TextView tv_reson;
        public LinearLayout ll_reson;
    }

    class ChildViewHolder {
        public View chil_vv;
        public ImageView iv_check;
        public TextView tv_content;
        public TextView tv_state;
        public RelativeLayout rl_ok;
        public View chil_vv3;
        public TextView tv_change;
        public TextView tv_dis;
        public TextView tv_submit;

        public LinearLayout ll_wu;
        public RelativeLayout rl;
        public TextView tv_yijian;
        public TextView tv_jian;
        public TextView tv_li;
    }
}
