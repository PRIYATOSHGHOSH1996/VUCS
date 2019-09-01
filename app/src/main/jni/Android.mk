LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := vucs
LOCAL_SRC_FILES := vucs.c

include $(BUILD_SHARED_LIBRARY)
