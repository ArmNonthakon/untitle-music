package com.example.core.data.model

import com.google.gson.annotations.SerializedName

data class Actions(
    @SerializedName("interrupting_playback") val interruptingPlayback: Boolean,
    @SerializedName("pausing") val pausing: Boolean,
    @SerializedName("resuming") val resuming: Boolean,
    @SerializedName("seeking") val seeking: Boolean,
    @SerializedName("skipping_next") val skippingNext: Boolean,
    @SerializedName("skipping_prev") val skippingPrev: Boolean,
    @SerializedName("toggling_repeat_context") val togglingRepeatContext: Boolean,
    @SerializedName("toggling_shuffle") val togglingShuffle: Boolean,
    @SerializedName("toggling_repeat_track") val togglingRepeatTrack: Boolean,
    @SerializedName("transferring_playback") val transferringPlayback: Boolean
)
