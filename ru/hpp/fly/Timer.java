package ru.hpp.fly;

import org.bukkit.entity.Player;

public class Timer implements Runnable {
    Player Human;
    public Timer(Player p) {
        Human = p;
    }
    @Override
    public void run() {
        ru.hpp.fly.fly.cooldown.put(Human, true);
    }
}
