package com.clb.utils;

public interface Operation {
    int apply(int lhs, int rhs);
    boolean handles(char op);
}
