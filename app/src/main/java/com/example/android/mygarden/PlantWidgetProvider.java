package com.example.android.mygarden;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.mygarden.ui.MainActivity;

public class PlantWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager manager, int imgRes, int id){
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.plant_widget);
        views.setImageViewResource(R.id.widget_plant_image,imgRes);
        views.setOnClickPendingIntent(R.id.widget_plant_image,pendingIntent);

        Intent intent = new Intent(context,PlantWateringService.class);
        intent.setAction(PlantWateringService.ACTION_WATER_PLANTS);
        PendingIntent pendIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_water_button,pendIntent);

        manager.updateAppWidget(id,views);
    }

    public static void updatePlantWidget(Context context, AppWidgetManager manager, int imgRes, int[] ids) {
        for (int id : ids) {
            updateAppWidget(context,manager,imgRes,id);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        PlantWateringService.startActionUpdatePlants(context);
    }
}
