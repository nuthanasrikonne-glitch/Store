package org.example;

import java.util.*;

class Log1 {
    int time;
    String user;
    String action;

    Log1(int time, String user, String action)
    {
        this.time = time;
        this.user = user;
        this.action = action;
    }
}

public class Solution {

    static final int MAX_TIME = 1800;

    // ✅ VALID SESSIONS
    public static  int countValidSessions(List<Log1>logs)
    {
        Map<String,Integer>activesession= new HashMap<>();

        int valid =0;
         for(Log1 log : logs)
         {
             int time=log.time;
             String user = log.user;
             String action = log.action;
             if("LOGIN".equals(action))
             {
                 activesession.put(user,time);
             } else if ("LOGOUT".equals(action))
             {
                 if(activesession.containsKey(user))
                 {
                     int logintime = activesession.get(user);
                     if(time-logintime<=MAX_TIME)
                     {
                         valid--;
                     }


                 }

             }
         }
         return valid;
    }
    // ❌ INVALID SESSIONS
    public static int countInvalidSessions(List<Log1> logs) {

        Map<String, Integer> activeSessions = new HashMap<>();
        int invalid = 0;

        for (Log1 log : logs) {

            String user = log.user;
            String action = log.action;
            int time = log.time;

            if ("LOGIN".equals(action)) {

                // LOGIN again without LOGOUT
                if (activeSessions.containsKey(user)) {
                    invalid++;
                }

                activeSessions.put(user, time);

            } else if ("LOGOUT".equals(action)) {

                // LOGOUT without LOGIN
                if (!activeSessions.containsKey(user)) {
                    invalid++;
                    continue;
                }

                int loginTime = activeSessions.get(user);

                // Session exceeds max time
                if (time - loginTime > MAX_TIME) {
                    invalid++;
                }

                activeSessions.remove(user);
            }
        }

        // LOGIN without LOGOUT till end
        invalid += activeSessions.size();

        return invalid;
    }

    public static void main(String[] args) {

        List<Log1> logs = List.of(
                new Log1(600, "u2", "LOGIN"),
                new Log1(100, "u1", "LOGIN"),
                new Log1(3000, "u3", "LOGIN"),
                new Log1(500, "u1", "LOGOUT"),
                new Log1(2500, "u2", "LOGOUT"),
                new Log1(3100, "u3", "LOGIN"),
                new Log1(4000, "u4", "LOGOUT"),
                new Log1(3300, "u3", "LOGOUT")
        );

        System.out.println("Valid Sessions: " + countValidSessions(logs));
        System.out.println("Invalid Sessions: " + countInvalidSessions(logs));
    }
}