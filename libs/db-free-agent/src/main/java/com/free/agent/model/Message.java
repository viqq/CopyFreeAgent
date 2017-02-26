package com.free.agent.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by antonPC on 28.07.15.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MESSAGE")
public class Message extends AbstractTable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long id;

    @Column(name = "AUTHOR_ID")
    private Long authorId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "TIME_OF_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfCreate;

    @Column(name = "TIME_OF_READ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfRead;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

}
