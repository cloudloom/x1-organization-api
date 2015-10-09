package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.organization.api.domain.Position;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ffazil
 * @since 16/05/15
 */
@Entity
@Table(name = "POSITION")
public class DefaultPosition extends BaseEntity implements Position{

    @Column(name = "NAME", nullable = false, unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "POSITION_TYPE", nullable = false, columnDefinition = "ENUM('TOP_LEVEL_EXECUTIVES', 'MID_LEVEL_EXECUTIVES', 'MANAGERIAL', 'FRONT_OFFICE', 'BACK_OFFICE', 'FIELD_STAFF', 'SUPPORT_STAFF', 'CUSTOMER_SERVICE_AGENT') default 'SUPPORT_STAFF'")
    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    @Column(name = "CODE", nullable = false, unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    private DefaultPosition parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPosition> children = new HashSet<DefaultPosition>(0);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PositionType getPositionType() {
        return positionType;
    }

    @Override
    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void addChild(DefaultPosition child) {
        child.setParent(this);
        this.children.add(child);
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultPosition that = (DefaultPosition) o;

        if (!code.equals(that.code)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultPosition position = (DefaultPosition) o;

        if (code != null ? !code.equals(position.code) : position.code != null) return false;
        if (name != null ? !name.equals(position.name) : position.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    public DefaultPosition getParent() {
        return parent;
    }

    public void setParent(DefaultPosition parent) {
        this.parent = parent;
    }

    public Set<DefaultPosition> getChildren() {
        return children;
    }

    public void setChildren(Set<DefaultPosition> children) {
        if(children != null && children.size() > 0) {
            children.stream().forEach(child -> {
                child.setParent(this);
            });
            this.children = children;
        }
    }
}