package com.pjzhong.leetcode;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user
 * and is able to see the 10 most recent tweets in the user's news feed. Your design should support
 * the following methods:
 *
 * <p>1. postTweet(userId, tweetId): Compose a new tweet.</p>
 * <p>2. getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each
 * item in the news feed must be posted by users who the user followed or by the user herself.
 * Tweets must be ordered from most recent to least recent.</p>
 * <p>3. follow(followerId, followeeId): Follower follows a followee.</p>
 * <p>4. unfollow(followerId, followeeId):Follower unfollows a followee.</p>
 *
 * @link https://leetcode.com/problems/design-twitter/
 * @since 2020年02月09日 21:10:14
 **/
public class Twitter {

  private Set<T> ts;
  private Map<Integer, Set<Integer>> follows;
  private AtomicLong order;

  public static class T implements Comparable<T> {

    private final int tid;
    private final int aid;
    private final long order;

    T(int aid, int tid, long order) {
      this.aid = aid;
      this.tid = tid;
      this.order = order;
    }

    int getId() {
      return tid;
    }


    int getAid() {
      return aid;
    }

    @Override
    public int compareTo(T o) {
      return Long.compare(o.order, order);
    }
  }

  /** Initialize your data structure here. */
  public Twitter() {
    ts = new ConcurrentSkipListSet<>();
    follows = new ConcurrentHashMap<>();
    order = new AtomicLong(1);
  }

  /** Compose a new tweet. */
  public void postTweet(int userId, int tweetId) {
    ts.add(new T(userId, tweetId, order.getAndIncrement()));
  }

  /**
   * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must
   * be posted by users who the user followed or by the user herself. Tweets must be ordered from
   * most recent to least recent.
   */
  public List<Integer> getNewsFeed(int userId) {
    Set<Integer> followees = follows.getOrDefault(userId, Collections.emptySet());
    return ts.stream()
        .filter(t -> t.getAid() == userId || followees.contains(t.getAid())).limit(10)
        .map(T::getId)
        .collect(Collectors.toList());
  }

  /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
  public void follow(int followerId, int followeeId) {
    follows.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
  }

  /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
  public void unfollow(int followerId, int followeeId) {
    follows.computeIfPresent(followerId, (k, v) -> {
      v.remove(followeeId);
      return v;
    });
  }
}
