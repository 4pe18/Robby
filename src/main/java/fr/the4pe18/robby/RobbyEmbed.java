package fr.the4pe18.robby;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class RobbyEmbed {
    private EmbedBuilder embedBuilder;

    public RobbyEmbed() {
        this.embedBuilder = new EmbedBuilder();
        this.init();
    }

    public RobbyEmbed(String title, String description) {
        this(title, null, description);
    }

    public RobbyEmbed(String title, String url, String description) {
        this.embedBuilder = new EmbedBuilder();
        this.init();
        this.getEmbedBuilder().setTitle(title, url);
        if (description != null && !description.isEmpty()) this.getEmbedBuilder().setDescription(description);
    }

    public RobbyEmbed init() {
        this.getEmbedBuilder().setColor(Color.red);
        this.getEmbedBuilder().setFooter("Bip. Robby. Bip boup. Je suis un robot.", Robby.getJdaInstance().getSelfUser().getAvatarUrl());
        return this;
    }

    public RobbyEmbed setColor(Color color) {
        this.getEmbedBuilder().setColor(color);
        return this;
    }

    public RobbyEmbed addField(String name, String value, Boolean inline) {
        this.getEmbedBuilder().addField(name, value, inline);
        return this;
    }

    public RobbyEmbed setThumbnail(String thumbnail) {
        this.getEmbedBuilder().setThumbnail(thumbnail);
        return this;
    }

    public RobbyEmbed setImage(String image) {
        this.getEmbedBuilder().setImage(image);
        return this;
    }

    public RobbyEmbed setAuthor(String name) {
        this.getEmbedBuilder().setAuthor(name);
        return this;
    }

    public RobbyEmbed setAuthor(String name, String iconUrl) {
        this.getEmbedBuilder().setAuthor(name, null, iconUrl);
        return this;
    }

    public RobbyEmbed setAuthor(String name, String url, String iconUrl) {
        this.getEmbedBuilder().setAuthor(name, url, iconUrl);
        return this;
    }

    public RobbyEmbed addBlankField(Boolean inline) {
        this.getEmbedBuilder().addBlankField(inline);
        return this;
    }

    public MessageEmbed build() {
        this.getEmbedBuilder().setTimestamp(Instant.now());
        return this.getEmbedBuilder().build();
    }

    public EmbedBuilder getEmbedBuilder() {
        return embedBuilder;
    }
}
