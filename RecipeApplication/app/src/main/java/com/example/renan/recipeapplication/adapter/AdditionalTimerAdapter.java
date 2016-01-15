package com.example.renan.recipeapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.recipeapplication.R;
import com.example.renan.recipeapplication.Util.Util;
import com.example.renan.recipeapplication.entities.AdditionalTimer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by c1284141 on 28/12/2015.
 */
public class AdditionalTimerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AdditionalTimer> mItens;
    private final static int VIEW_TYPE_ITEM = 1, VIEW_TYPE_LAST = 2;
    public int mPosition;

    public AdditionalTimerAdapter(Context context, List<AdditionalTimer> itens) {
        mContext = context;
        mItens = itens;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_additional_timer, parent, false);
            viewHolder = new AdditionalTimerViewHolder(view);
        } else if (viewType == VIEW_TYPE_LAST) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_last_additional_timer, parent, false);
            viewHolder = new LastViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            mPosition = holder.getLayoutPosition();
            final AdditionalTimerViewHolder viewHolder = (AdditionalTimerViewHolder) holder;

            mItens.get(position).setTimer(new Timer());
            final Handler handler = new Handler();

            viewHolder.tvTimer.setText(mItens.get(position).getTimerStr());
            viewHolder.tvDescriptionTimer.setText(mItens.get(position).getDescription());

            viewHolder.ivStartStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mItens.get(position).getActiveTimer()) {
                        startTimer(handler, viewHolder, position);
                    } else {
                        stopTimerTask(viewHolder, position);
                    }
                }
            });

            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Remover Alarme")
                            .setMessage("Tem certeza que deseja remover o alarme " + viewHolder.tvDescriptionTimer.getText() + "?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mItens.get(position).getTimer().cancel();
                                    mItens.get(position).setTimer(null);
                                    mItens.remove(position);
                                    notifyItemRemoved(position);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(R.drawable.ic_alert_warning)
                            .show();
                }
            });

        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.getWindow();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                    dialog.setContentView(R.layout.custom_dialog_additional_timer);

                    bindElementsDialog(dialog);

                    dialog.show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mItens.size()) ? VIEW_TYPE_LAST : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mItens.size() + 1;
    }

    public AdditionalTimer getSelectedItem() {
        return mItens.get(mPosition);
    }

    public static class AdditionalTimerViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivStartStop, ivDelete;
        public TextView tvTimer, tvDescriptionTimer;

        public AdditionalTimerViewHolder(View itemView) {
            super(itemView);
            ivStartStop = (ImageView) itemView.findViewById(R.id.iv_start_stop);
            tvTimer = (TextView) itemView.findViewById(R.id.et_timer);
            tvDescriptionTimer = (TextView) itemView.findViewById(R.id.et_description_timer);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete_timer);
        }
    }

    public static class LastViewHolder extends RecyclerView.ViewHolder {
        public LastViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void startTimer(Handler handler, AdditionalTimerViewHolder viewHolder, int position) {
        //initialize the TimerTask's job
        TimerTask timerTask = initializeTimerTask(handler, viewHolder, position);

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        getSelectedItem().getTimer().schedule(timerTask, 1000, 1000); //

        getSelectedItem().setActiveTimer(true);
        viewHolder.ivStartStop.setImageResource(R.drawable.ic_pause);
    }

    public void stopTimerTask(AdditionalTimerViewHolder viewHolder, int position) {
        //stop the timer, if it's not already null
        if (getSelectedItem().getTimer() != null) {
            getSelectedItem().getTimer().cancel();
            getSelectedItem().setTimer(null);
            getSelectedItem().setActiveTimer(false);
            viewHolder.ivStartStop.setImageResource(R.drawable.ic_play);
        }
    }

    public TimerTask initializeTimerTask(final Handler handler, final AdditionalTimerViewHolder viewHolder, final int position) {
        return new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        int hour, min, sec;

                        hour = Integer.parseInt(viewHolder.tvTimer.getText().toString().substring(0, 2).replaceAll(":", ""));
                        min = Integer.parseInt(viewHolder.tvTimer.getText().toString().substring(3, 5).replaceAll(":", ""));
                        sec = Integer.parseInt(viewHolder.tvTimer.getText().toString().substring(6, 8).replaceAll(":", ""));

                        sec = sec - 1;
                        if (sec == -1) {
                            min = min - 1;
                            sec = 59;
                        }
                        if (min == -1) {
                            hour = hour - 1;
                            min = 59;
                        }
                        viewHolder.tvTimer.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                        if (sec == 0 && min == 0 && hour == 0) {
                            Toast.makeText(mContext, "Acabou", Toast.LENGTH_SHORT).show();
                            viewHolder.tvTimer.setText(getSelectedItem().getTimerStr());
                            stopTimerTask(viewHolder, position);
                        }
                    }
                });
            }
        };
    }

    private void bindElementsDialog(final Dialog dialog) {
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
        TextView tvAdd = (TextView) dialog.findViewById(R.id.tv_add_timer);
        final EditText etTimer = (EditText) dialog.findViewById(R.id.et_timer);
        final EditText etDescription = (EditText) dialog.findViewById(R.id.et_description_timer);

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar recipeCalendar = Calendar.getInstance(Util.LOCALE_PT_BR);

                Boolean isValid = true;

                if (TextUtils.isEmpty(etDescription.getText().toString().trim())) {
                    etDescription.setError(mContext.getString(R.string.msg_mandatory));
                    etDescription.setText("");
                    etDescription.requestFocus();
                    isValid = false;
                }
                if (TextUtils.isEmpty(etTimer.getText().toString().trim())) {
                    etTimer.setError(mContext.getString(R.string.msg_mandatory));
                    etTimer.setText("");
                    etTimer.requestFocus();
                    isValid = false;
                }
                if (!verifyPrepareTime(recipeCalendar, etTimer)) {
                    etTimer.requestFocus();
                    isValid = false;
                }
                if (isValid) {
                    AdditionalTimer additionalTimer = new AdditionalTimer();
                    additionalTimer.setTimerStr(etTimer.getText().toString() + ":00");
                    additionalTimer.setDescription(etDescription.getText().toString());
                    additionalTimer.setActiveTimer(false);
                    mItens.add(additionalTimer);
                    notifyItemInserted(mItens.size());
                    dialog.dismiss();
                }
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private boolean verifyPrepareTime(Calendar recipeCalendar, EditText etTimer) {
        final String timeText = etTimer.getText().toString().trim();
        if (!TextUtils.isEmpty(timeText)) {
            try {
                final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Util.LOCALE_PT_BR);
                timeFormat.setLenient(false);
                timeFormat.parse(timeText);
                if (recipeCalendar != null) {
                    final String[] timeTextArray = timeText.split("[:]");
                    recipeCalendar.set(Calendar.HOUR, Integer.valueOf(timeTextArray[0]));
                    recipeCalendar.set(Calendar.MINUTE, Integer.valueOf(timeTextArray[1]));
                }
            } catch (ParseException parseException) {
                etTimer.setError(mContext.getString(R.string.msg_invalid_time));
                etTimer.requestFocus();
                return false;
            }

            if (!timeText.substring(2, 3).equals(":") || timeText.length() != 5) {
                etTimer.setError(mContext.getString(R.string.msg_invalid_time));
                etTimer.requestFocus();
                return false;
            }
        }
        return true;
    }
}
