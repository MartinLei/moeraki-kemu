package de.htwg.se.moerakikemu.persistence.hibernate.util;

import org.hibernate.Session;
import de.htwg.se.moerakikemu.model.IField;

@FunctionalInterface
public interface TransactionWithReturn {
    IField toTransact(Session session);
}