package com.prase.wf.util;

import java.util.List;

import android.R.integer;

public class WeatherDataStruct
{
	 private int error;  
     private String status;  
     private String date;  
     private List<Results> results;  
     public int getError()   
     {  
         return error;  
     }  
     public void setError(int error)   
     {  
         this.error = error;  
     }  
       
     public String getStatus()   
     {  
         return status;  
     }  
     public void setStatus(String status)   
     {  
         this.status = status;  
     }  
     public String getDate()   
     {  
         return date;  
     }  
     public void setDate(String date)   
     {  
         this.date = date;  
     }  
     public List<Results> getResults()   
     {  
         return results;  
     }  
     public void setResults(List<Results> results)   
     {  
         this.results = results;  
     } 
     @Override
    public String toString()
    {
    	// TODO Auto-generated method stub
    	 return "WeatherDataStruct [error= "+Integer.toString(error)+",status="+ status  
                 + ", date=" + date + ", results=" + results + "]"; 
    	//return super.toString();
    }
}
