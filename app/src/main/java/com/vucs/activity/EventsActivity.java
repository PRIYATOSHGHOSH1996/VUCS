package com.vucs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.vucs.R;
import com.vucs.model.EventModel;
import com.vucs.viewmodel.EventViewModel;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsActivity extends AppCompatActivity {

    TextView header_text, month_name;
    EventViewModel eventViewModel;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM  yyyy", Locale.getDefault());
    private String TAG = "Event activity";
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        header_text = findViewById(R.id.header_text);
        header_text.setText(getString(R.string.events));
        month_name = findViewById(R.id.month_name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        month_name.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        final List<EventModel> mutableBookings = new ArrayList<>();
        final ListView bookingsListView = findViewById(R.id.bookings_listview);
        final ListAdapter adapter = new ListAdapter(this);
        bookingsListView.setAdapter(adapter);
        // below allows you to configure color for the current day in the month
        // compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(true);
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        setToMidnight(currentCalender);


        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        updateAdapter();


        //compactCalendarView.setIsRtl(true);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                month_name.setText(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        EventModel eventModel = (EventModel) booking.getData();
                        assert eventModel != null;
                        mutableBookings.add(eventModel);
                    }
                    adapter.addEvent(mutableBookings);
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                month_name.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateAdapter();
            }
        };
    }


    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public void backMonthClick(View view) {
        compactCalendarView.scrollLeft();
    }

    public void forwardMonthClick(View view) {
        compactCalendarView.scrollRight();
    }

    private void updateAdapter() {
        List<Event> events1 = new ArrayList<>();
        for (EventModel eventModel : eventViewModel.getAllEvent()) {
            events1.add(new Event(Color.argb(255, 169, 68, 65), eventModel.getDate().getTime(), eventModel));

        }
        compactCalendarView.removeAllEvents();
        compactCalendarView.addEvents(events1);
        compactCalendarView.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onresume");
        try {
            updateAdapter();
            registerReceiver(broadcastReceiver, new IntentFilter(getString(R.string.event_broadcast_receiver)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onpause");
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ListAdapter extends BaseAdapter {
        WeakReference<Context> weakReference;
        List<EventModel> eventModels = Collections.emptyList();

        public ListAdapter(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        void addEvent(List<EventModel> eventModels) {
            this.eventModels = eventModels;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return eventModels.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.item_event, parent, false);
            TextView title = view.findViewById(R.id.event_title);
            TextView des = view.findViewById(R.id.event_Description);
            EventModel eventModel = eventModels.get(position);
            title.setText(eventModel.getEventTitle());
            des.setText(eventModel.getEventDescription());
            return view;
        }
    }
}
