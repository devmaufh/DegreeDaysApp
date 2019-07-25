package com.devmaufh.degreedaysapp.Utilities

import com.devmaufh.degreedaysapp.Entities.Insect
import org.json.JSONObject

class Utilities {
    companion object{
        fun parseInsectToJson(insect:Insect):JSONObject{
            var json=JSONObject()
            json.put("id_insect",insect.insect_id)
            json.put("name",insect.name)
            json.put("tu",insect.tu)
            json.put("tl",insect.tl)
            json.put("registration_date",insect.registration_date)
            json.put("email",insect.email)
            return json
        }
    }
}