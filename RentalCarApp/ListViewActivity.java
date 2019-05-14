package [your package name here];

public class ListViewActivity extends ListActivity {

    List<RentalCar> rentalCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_view);

        getRentalCar();
    }

    protected void getRentalCar(){
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));
        RestAPIClient.get(ListViewActivity.this, "cars.json", headers.toArray(new Header[headers.size()]), null, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                rentalCars = new ArrayList<RentalCar>();
                for(int i = 0; i < response.length(); i++){
                    try{
                        rentalCars.add(new RentalCar(response.getJSONObject(i)));
                    }catch(Exception ex){ex.printStackTrace();}
                }
                setListAdapter(new ArrayAdapter<RentalCar>(ListViewActivity.this,R.layout.activity_car_list,R.id.txtCarName, rentalCars));
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                rentalCars = new ArrayList<RentalCar>();
                Iterator<String> keys = response.keys();
                while(keys.hasNext()){
                    String key = keys.next();
                    try{
                        rentalCars.add(new RentalCar(response.getJSONObject(key)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setListAdapter(new ArrayAdapter<RentalCar>(ListViewActivity.this,R.layout.activity_car_list,R.id.txtCarName, rentalCars));

            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        RentalCar selectedCar = rentalCars.get(position);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ListViewActivity.this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", selectedCar.getId());
        editor.putString("name", selectedCar.getName());
        editor.putString("brand", selectedCar.getBrand());
        editor.putFloat("rentalCostPerDay", (float)selectedCar.getRentalCostPerDay()); //since costperday is double, need to cast to flost
        editor.putString("color", selectedCar.getColor());
        editor.putInt("position", position);
        editor.commit();

        startActivity(new Intent(ListViewActivity.this, CalculateCostActivity.class));
    }

}

