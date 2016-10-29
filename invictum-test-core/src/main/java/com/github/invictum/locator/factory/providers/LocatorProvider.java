package com.github.invictum.locator.factory.providers;

import org.openqa.selenium.By;

public interface LocatorProvider {
    boolean isAcceptableTo(String locator);
    By wrap(String locator);
}
