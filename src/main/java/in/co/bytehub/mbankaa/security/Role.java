package in.co.bytehub.mbankaa.security;

import java.util.List;
import java.util.Objects;


public class Role {
    private String name;
    private List<Role> children;

    public Role() {
    }

    public Role(String name, List<Role> children) {
        this.name = name;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public List<Role> getChildren() {
        return children;
    }

    public Role setChildren(List<Role> children) {
        this.children = children;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name) && Objects.equals(children, role.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children);
    }
}
