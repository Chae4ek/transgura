package chae4ek.transgura.ecs.util.structures;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class SetGuard<E> implements Iterable<E> {

  private final Set<E> set;

  public SetGuard(final Set<E> set) {
    this.set = set;
  }

  public boolean isEmpty() {
    return set.isEmpty();
  }

  public int size() {
    return set.size();
  }

  public boolean contains(final Object o) {
    return set.contains(o);
  }

  public boolean containsAll(final Collection<?> c) {
    return set.containsAll(c);
  }

  public Object[] toArray() {
    return set.toArray();
  }

  public <T> T[] toArray(final T[] a) {
    return set.toArray(a);
  }

  public Stream<E> stream() {
    return set.stream();
  }

  public Stream<E> parallelStream() {
    return set.parallelStream();
  }

  @Override
  public void forEach(final Consumer<? super E> action) {
    set.forEach(action);
  }

  @Override
  public Iterator<E> iterator() {
    return set.iterator();
  }

  @Override
  public Spliterator<E> spliterator() {
    return set.spliterator();
  }

  @Override
  public String toString() {
    return set.toString();
  }

  @Override
  public boolean equals(final Object o) {
    return this == o
        || o != null && o.getClass() == SetGuard.class && set.equals(((SetGuard<?>) o).set);
  }

  @Override
  public int hashCode() {
    return set.hashCode();
  }
}
