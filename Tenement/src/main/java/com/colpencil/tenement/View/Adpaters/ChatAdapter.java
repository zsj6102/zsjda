package com.colpencil.tenement.View.Adpaters;

import android.widget.BaseAdapter;

/**
 * @Description:  聊天的适配器
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/25
 */
public class ChatAdapter{
//    extends BaseAdapter {
  /*  private final Context cxt;
    private List<Message> datas = null;
    private KJBitmap kjb;
    private ChatActivity.OnChatItemClickListener listener;
    private Typeface emojitf;

    public ChatAdapter(Context cxt, List<Message> datas, ChatActivity.OnChatItemClickListener listener) {
        this.cxt = cxt;
        if (datas == null) {
            datas = new ArrayList<Message>(0);
        }
        this.datas = datas;
        kjb = new KJBitmap();
        this.listener = listener;
        if (!"smartisan".equals(Build.MANUFACTURER)) {
            try {
                emojitf = Typeface.createFromAsset(cxt.getAssets(), "fonts/emoji.ttf");
            } catch (Exception e) {
            }
        }
    }

    public void refresh(List<Message> datas) {
        if (datas == null) {
            datas = new ArrayList<Message>(0);
        }
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getIsSend() ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        final ViewHolder holder;
        final Message data = datas.get(position);
        if (v == null) {
            holder = new ViewHolder();
            if (data.getIsSend()) {
                v = View.inflate(cxt, org.kymjs.chat.R.layout.chat_item_list_right, null);
            } else {
                v = View.inflate(cxt, org.kymjs.chat.R.layout.chat_item_list_left, null);
            }
            holder.layout_content = (RelativeLayout) v.findViewById(org.kymjs.chat.R.id.chat_item_layout_content);
            holder.img_avatar = (ImageView) v.findViewById(org.kymjs.chat.R.id.chat_item_avatar);
            holder.img_chatimage = (ImageView) v.findViewById(org.kymjs.chat.R.id.chat_item_content_image);
            holder.img_sendfail = (ImageView) v.findViewById(org.kymjs.chat.R.id.chat_item_fail);
            holder.progress = (ProgressBar) v.findViewById(org.kymjs.chat.R.id.chat_item_progress);
            holder.tv_chatcontent = (TextView) v.findViewById(org.kymjs.chat.R.id.chat_item_content_text);
            holder.tv_date = (TextView) v.findViewById(org.kymjs.chat.R.id.chat_item_date);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tv_date.setText(StringUtils.friendlyTime(StringUtils.getDataTime("yyyy-MM-dd " +
                "HH:mm:ss")));  // 这里是更改时间的
        holder.tv_date.setVisibility(View.VISIBLE);

        if (emojitf != null) {
            holder.tv_chatcontent.setTypeface(emojitf);
        }

        //如果是文本类型，则隐藏图片，如果是图片则隐藏文本
        if (data.getType() == Message.MSG_TYPE_TEXT) {
            holder.img_chatimage.setVisibility(View.GONE);
            holder.tv_chatcontent.setVisibility(View.VISIBLE);
            if (data.getContent().contains("href")) {
                holder.tv_chatcontent = UrlUtils.handleHtmlText(holder.tv_chatcontent, data
                        .getContent());
            } else {
                holder.tv_chatcontent = UrlUtils.handleText(holder.tv_chatcontent, data
                        .getContent());
            }
        } else {
            holder.tv_chatcontent.setVisibility(View.GONE);
            holder.img_chatimage.setVisibility(View.VISIBLE);

            //如果内存缓存中有要显示的图片，且要显示的图片不是holder复用的图片，则什么也不做，否则显示一张加载中的图片
            if (kjb.getMemoryCache(data.getContent()) != null && data.getContent() != null &&
                    data.getContent().equals(holder.img_chatimage.getTag())) {
            } else {
                holder.img_chatimage.setImageResource(org.kymjs.chat.R.drawable.default_head);
            }
            kjb.display(holder.img_chatimage, data.getContent(), 300, 300);
        }

        //如果是表情或图片，则不显示气泡，如果是图片则显示气泡
        if (data.getType() != Message.MSG_TYPE_TEXT) {
            holder.layout_content.setBackgroundResource(android.R.color.transparent);
        } else {
            if (data.getIsSend()) {
                holder.layout_content.setBackgroundResource(org.kymjs.chat.R.drawable.chat_to_bg_selector);
            } else {
                holder.layout_content.setBackgroundResource(org.kymjs.chat.R.drawable.chat_from_bg_selector);
            }
        }

        //显示头像
        if (data.getIsSend()) {
            kjb.display(holder.img_avatar, data.getFromUserAvatar());
        } else {
            kjb.display(holder.img_avatar, data.getToUserAvatar());
        }

        if (listener != null) {
            holder.tv_chatcontent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTextClick(position);
                }
            });
            holder.img_chatimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (data.getType()) {
                        case Message.MSG_TYPE_PHOTO:
                            listener.onPhotoClick(position);
                            break;
                        case Message.MSG_TYPE_FACE:
                            listener.onFaceClick(position);
                            break;
                    }
                }
            });
        }

        //消息发送的状态
        switch (data.getState()) {
            case Message.MSG_STATE_FAIL:
                holder.progress.setVisibility(View.GONE);
                holder.img_sendfail.setVisibility(View.VISIBLE);
                break;
            case Message.MSG_STATE_SUCCESS:
                holder.progress.setVisibility(View.GONE);
                holder.img_sendfail.setVisibility(View.GONE);
                break;
            case Message.MSG_STATE_SENDING:
                holder.progress.setVisibility(View.VISIBLE);
                holder.img_sendfail.setVisibility(View.GONE);
                break;
        }
        return v;
    }

    static class ViewHolder {
        TextView tv_date;
        ImageView img_avatar;
        TextView tv_chatcontent;
        ImageView img_chatimage;
        ImageView img_sendfail;
        ProgressBar progress;
        RelativeLayout layout_content;
    }*/
}
