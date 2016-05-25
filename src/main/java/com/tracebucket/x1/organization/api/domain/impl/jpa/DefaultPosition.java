package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.organization.api.domain.Position;
import com.tracebucket.x1.organization.api.enums.converter.PositionTypeConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ffazil
 * @since 16/05/15
 * JPA Entity For Position
 */
@Entity
@Table(name = "POSITION")
public class DefaultPosition extends BaseEntity implements Position{

    @Column(name = "NAME", nullable = false, unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "POSITION_TYPE")
    @Convert(converter = PositionTypeConverter.class)
    private PositionType positionType;

    @Column(name = "CODE", nullable = false, unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    private DefaultPosition parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPosition> children = new HashSet<DefaultPosition>(0);

    /**
     * Get Name
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get PositionType
     * @return
     */
    @Override
    public PositionType getPositionType() {
        return positionType;
    }

    /**
     * Set PositionType
     * @param positionType
     */
    @Override
    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    /**
     * Get Code
     * @return
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Set Code
     * @param code
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Add Child Position
     * @param child
     */
    @Override
    public void addChild(DefaultPosition child) {
        //set childs parent as 'this' defaultPosition
        child.setParent(this);
        //add child to 'this' children
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

    /**
     * Get Parent
     * @return
     */
    public DefaultPosition getParent() {
        return parent;
    }

    /**
     * Set Parent
     * @param parent
     */
    public void setParent(DefaultPosition parent) {
        this.parent = parent;
    }

    /**
     * Get Children
     * @return
     */
    public Set<DefaultPosition> getChildren() {
        return children;
    }

    /**
     * Set Children
     * @param children
     */
    public void setChildren(Set<DefaultPosition> children) {
        //if children is not null and children size is greater than zero
        if(children != null && children.size() > 0) {
            // stream children and set parent of all children as 'this'
            children.stream().forEach(child -> {
                child.setParent(this);
            });
            this.children = children;
        }
    }
}